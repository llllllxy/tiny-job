package org.tinycloud.tinyjob.utils.http;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tinycloud.tinyjob.utils.JsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * <p>
 * Httpclient请求工具类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-05-30 12:56
 */
public class HttpUtils {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 连接主机超时（30s）
     */
    public static final int HTTP_CONNECT_TIMEOUT = 30 * 1000;

    /**
     * 从主机读取数据超时（30s）
     */
    public static final int HTTP_READ_TIMEOUT = 30 * 1000;

    /**
     * HTTP成功状态码（200）
     */
    public static final int HTTP_SUCCESS_STATUS_CODE = 200;

    /**
     * 禁止实例化
     */
    private HttpUtils() {
    }


    /**
     * get请求
     *
     * @param url       请求地址
     * @param paramMap  请求参数
     * @param headerMap 请求头
     * @return 请求结果
     */
    public static String get(String url, Map<String, Object> paramMap, Map<String, Object> headerMap, Map<String, Object> otherMap) throws Exception {
        int retryTimes = (otherMap == null || otherMap.get("failRetryTimes") == null) ? 0
                : Integer.parseInt(otherMap.get("failRetryTimes").toString()); // 重试次数，默认0次；如果是3的话，则最多会执行4次
        int executorTimeout = (otherMap == null || otherMap.get("executorTimeout") == null) ? HTTP_READ_TIMEOUT
                : Integer.parseInt(otherMap.get("executorTimeout").toString());

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        // 链接超时和读取超时时间设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(executorTimeout)
                .setConnectTimeout(HTTP_CONNECT_TIMEOUT)
                .build();
        try {
            URIBuilder builder = new URIBuilder(url);
            if (null != paramMap && !paramMap.isEmpty()) {
                // 创建参数队列
                List<NameValuePair> formParams = new ArrayList<>();
                for (Entry<String, Object> entry : paramMap.entrySet()) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                builder.setParameters(formParams);
            }
            // 设置参数
            HttpGet httpGet = new HttpGet(builder.build());
            httpGet.setConfig(requestConfig);

            if (null != headerMap && !headerMap.isEmpty()) {
                for (Entry<String, Object> entry : headerMap.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue().toString());
                }
            }
            // 发送请求
            int retryCount = 0;
            do {
                response = httpclient.execute(httpGet);
                result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                if (response.getStatusLine().getStatusCode() != HTTP_SUCCESS_STATUS_CODE) {
                    logger.error("Error in get Request URL is [{}], params [{}]. Result:[{}]", url, paramMap, result);
                    HttpClientUtils.closeQuietly(response);
                    retryCount++;
                } else {
                    break;
                }
            } while (retryCount <= retryTimes);
            if (retryCount > retryTimes) {
                throw new RuntimeException("Exceeded maximum retry attempts");
            }
        } catch (Exception e) {
            logger.error("Error in get ", e);
            throw e;
        } finally {
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpclient);
        }
        return result;
    }

    /**
     * post-formData请求
     *
     * @param url           请求地址
     * @param formDataParam 请求参数
     * @param headerMap     请求头
     * @return 请求结果
     */
    public static String post(String url, Map<String, Object> formDataParam, Map<String, Object> headerMap, Map<String, Object> otherMap) throws Exception {
        int retryTimes = (otherMap == null || otherMap.get("failRetryTimes") == null) ? 0
                : Integer.parseInt(otherMap.get("failRetryTimes").toString());
        int executorTimeout = (otherMap == null || otherMap.get("executorTimeout") == null) ? HTTP_READ_TIMEOUT
                : Integer.parseInt(otherMap.get("executorTimeout").toString());

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        // 超时时间设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(executorTimeout)
                .setConnectTimeout(HTTP_CONNECT_TIMEOUT).build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        try {
            if (null != formDataParam && formDataParam.size() > 0) {
                // 创建参数队列
                List<NameValuePair> formParams = new ArrayList<>();
                for (Entry<String, Object> entry : formDataParam.entrySet()) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                // 设置参数
                UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(formParams, StandardCharsets.UTF_8);
                httpPost.setEntity(urlEntity);
            }
            if (null != headerMap && !headerMap.isEmpty()) {
                for (Entry<String, Object> entry : headerMap.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue().toString());
                }
            }

            // 发送请求
            int retryCount = 0;
            do {
                response = httpclient.execute(httpPost);
                result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                if (response.getStatusLine().getStatusCode() != HTTP_SUCCESS_STATUS_CODE) {
                    logger.error("Error in post Request URL is [{}], params [{}]. Result:[{}]", url, formDataParam, result);
                    HttpClientUtils.closeQuietly(response);
                    retryCount++;
                } else {
                    break;
                }
            } while (retryCount <= retryTimes);
            if (retryCount > retryTimes) {
                throw new RuntimeException("Exceeded maximum retry attempts");
            }
        } catch (Exception e) {
            logger.error("Error in post ", e);
            throw e;
        } finally {
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpclient);
        }
        return result;
    }

    /**
     * post-json
     *
     * @param url       请求地址
     * @param paramMap  请求参数
     * @param headerMap 请求头
     * @return 请求结果
     */
    public static String postJson(String url, Map<String, Object> paramMap, Map<String, Object> headerMap, Map<String, Object> otherMap) throws Exception {
        int retryTimes = (otherMap == null || otherMap.get("failRetryTimes") == null) ? 0
                : Integer.parseInt(otherMap.get("failRetryTimes").toString());
        int executorTimeout = (otherMap == null || otherMap.get("executorTimeout") == null) ? HTTP_READ_TIMEOUT
                : Integer.parseInt(otherMap.get("executorTimeout").toString());

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        // 超时时间设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(executorTimeout)
                .setConnectTimeout(HTTP_CONNECT_TIMEOUT).build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        // 设置请求参数和请求头
        String jsonParam = JsonUtils.writeValueAsString(paramMap);
        if (StringUtils.isNotEmpty(jsonParam)) {
            StringEntity entity = new StringEntity(jsonParam, StandardCharsets.UTF_8);
            // entity.setContentEncoding("UTF-8"); // 这个参数设置有问题，部分接口在设置这个参数后无法访问
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
        }
        if (null != headerMap && !headerMap.isEmpty()) {
            for (Entry<String, Object> entry : headerMap.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        try {
            // 发送请求
            int retryCount = 0;
            do {
                response = httpclient.execute(httpPost);
                result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                if (response.getStatusLine().getStatusCode() != HTTP_SUCCESS_STATUS_CODE) {
                    logger.error("Error in postJson Request URL is [{}], params [{}]. Result:[{}]", url, jsonParam, result);
                    HttpClientUtils.closeQuietly(response);
                    retryCount++;
                } else {
                    break;
                }
            } while (retryCount <= retryTimes);
            if (retryCount > retryTimes) {
                throw new RuntimeException("Exceeded maximum retry attempts");
            }
        } catch (Exception e) {
            logger.error("Error in postJson ", e);
            throw e;
        } finally {
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpclient);
        }
        return result;
    }

    /**
     * 下载文件-get
     *
     * @param url 请求地址
     * @return 请求结果
     */
    public static byte[] download(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = null;
        // 设置超时时间、请求时间、socket 时间都为 15 秒，允许重定向
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(15000)
                .setConnectionRequestTimeout(15000)
                .setSocketTimeout(15000)
                .setRedirectsEnabled(true)
                .build();
        httpGet.setConfig(requestConfig);
        byte[] fileByte = null;
        try {
            // 如果连接不上服务器，则抛出:java.net.ConnectException: Connection refused: connect
            httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HTTP_SUCCESS_STATUS_CODE) {
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = httpEntity.getContent();
                if (inputStream != null) {
                    fileByte = IOUtils.toByteArray(inputStream);
                    IOUtils.closeQuietly(inputStream);
                }
            }
        } catch (IOException e) {
            logger.error("Error in download = ", e);
            throw e;
        } finally {
            HttpClientUtils.closeQuietly(httpResponse);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return fileByte;
    }


    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String url = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php";
        String url2 = "https://opendata.baidu.com/api.php";

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("resource_id", "6006");
        hashMap.put("format", "json");
        hashMap.put("query", "101.43.9.251");
        hashMap.put("oe", "utf8");

        Map<String, Object> otherMap = new HashMap<>();
        otherMap.put("failRetryTimes", 3);
        otherMap.put("executorTimeout", 5000);

        String result = get(url, hashMap, null, otherMap);
        System.out.println(result);
    }
}
