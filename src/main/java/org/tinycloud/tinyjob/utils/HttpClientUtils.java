package org.tinycloud.tinyjob.utils;

import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
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
public class HttpClientUtils {
    final static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);


    /**
     * 连接主机超时（30s）
     */
    public static final int HTTP_CONNECT_TIMEOUT_30S = 30 * 1000;

    /**
     * 从主机读取数据超时（3min）
     */
    public static final int HTTP_READ_TIMEOUT_3MIN = 180 * 1000;

    /**
     * HTTP成功状态码（200）
     */
    public static final int HTTP_SUCCESS_STATUS_CODE = 200;

    private HttpClientUtils() {
    }


    /**
     * get请求
     *
     * @param url
     * @param paramMap
     * @param headerMap
     * @return
     */
    public static String get(String url, Map<String, Object> paramMap, Map<String, Object> headerMap) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        // 超时时间设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(HTTP_READ_TIMEOUT_3MIN)
                .setConnectTimeout(HTTP_CONNECT_TIMEOUT_30S).build();

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
            response = httpclient.execute(httpGet);
            result = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
            if (response.getStatusLine().getStatusCode() != HTTP_SUCCESS_STATUS_CODE) {
                logger.error("Error in getMap. Request URL is [{}], params [{}]. Result:[{}]", url, paramMap, result);
            }
        } catch (Exception e) {
            logger.error("Error in getMap", e);
            throw e;
        } finally {
            org.apache.http.client.utils.HttpClientUtils.closeQuietly(httpclient);
            org.apache.http.client.utils.HttpClientUtils.closeQuietly(response);
        }
        return result;
    }

    /**
     * postFormData
     *
     * @param url
     * @param formDataParam
     * @return
     */
    public static String post(String url, Map<String, Object> formDataParam, Map<String, Object> headerMap) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        // 超时时间设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(HTTP_READ_TIMEOUT_3MIN)
                .setConnectTimeout(HTTP_CONNECT_TIMEOUT_30S).build();

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
                UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(formParams, Charsets.UTF_8);
                httpPost.setEntity(urlEntity);
            }
            if (null != headerMap && !headerMap.isEmpty()) {
                for (Entry<String, Object> entry : headerMap.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue().toString());
                }
            }

            // 发送请求
            response = httpclient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
            if (response.getStatusLine().getStatusCode() != HTTP_SUCCESS_STATUS_CODE) {
                logger.error("Error in postFormData. Request URL is [{}], params [{}]. Result:[{}]", url, formDataParam, result);
            }
        } catch (Exception e) {
            logger.error("Error in postFormData", e);
            throw e;
        } finally {
            org.apache.http.client.utils.HttpClientUtils.closeQuietly(httpclient);
            org.apache.http.client.utils.HttpClientUtils.closeQuietly(response);
        }
        return result;
    }

    /**
     * postJson
     *
     * @param url
     * @param jsonParam
     * @return
     */
    public static String postJson(String url, String jsonParam, Map<String, Object> headerMap) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        // 超时时间设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(HTTP_READ_TIMEOUT_3MIN)
                .setConnectTimeout(HTTP_CONNECT_TIMEOUT_30S).build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        // 设置请求头和请求参数
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
            response = httpclient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
            if (response.getStatusLine().getStatusCode() != HTTP_SUCCESS_STATUS_CODE) {
                logger.error("Error in postJson. Request URL is [{}], params [{}]. Result:[{}]", url, jsonParam, result);
            }
        } catch (Exception e) {
            logger.error("Error in postJson", e);
            throw e;
        } finally {
            org.apache.http.client.utils.HttpClientUtils.closeQuietly(httpclient);
            org.apache.http.client.utils.HttpClientUtils.closeQuietly(response);
        }
        return result;
    }


    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String url = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php";
        String url2 = "https://opendata.baidu.com/api.php";

        HashMap hashMap = new HashMap();
        hashMap.put("resource_id", "6006");
        hashMap.put("format", "json");
        hashMap.put("query", "101.43.9.251");
        hashMap.put("oe", "utf8");

        String map = get(url, hashMap, null);
        System.out.println(map);

        String map2 = get(url2, hashMap, null);
        System.out.println(map2);
    }
}
