package org.tinycloud.tinyjob.utils.http;

import org.tinycloud.tinyjob.constant.JobTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Http策略类（Map+函数式接口，实现策略者模式）
 * 参考自：https://www.1024sou.com/article/406865.html
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-19 12:56
 */
public class HttpStrategy {

    private static final Map<String, HttpFunction<String, Map<String, Object>, Map<String, Object>, Map<String, Object>, String>> httpTypeMap
            = new HashMap<>();

    /*
     * 使用static块初始化业务逻辑分派Map，其中value 存放的是 lambda表达式
     */
    static {
        httpTypeMap.put(JobTypeEnum.GET.getCode(), (finalUrl, paramMap, headerMap, otherMap) -> {
            return HttpUtils.get(finalUrl, paramMap, headerMap, otherMap);
        });
        httpTypeMap.put(JobTypeEnum.POST.getCode(), (finalUrl, paramMap, headerMap, otherMap) -> {
            return HttpUtils.post(finalUrl, paramMap, headerMap, otherMap);
        });
        httpTypeMap.put(JobTypeEnum.POST_JSON.getCode(), (finalUrl, paramMap, headerMap, otherMap) -> {
            return HttpUtils.postJson(finalUrl, paramMap, headerMap, otherMap);
        });
    }


    public static String getResult(String jobType,
                                   String finalUrl,
                                   Map<String, Object> paramMap,
                                   Map<String, Object> headerMap,
                                   Map<String, Object> otherMap) throws Exception {
        // 根据jobType去查函数式接口
        HttpFunction<String, Map<String, Object>, Map<String, Object>, Map<String, Object>, String> result = httpTypeMap.get(jobType);
        if (result != null) {
            return result.apply(finalUrl, paramMap, headerMap, otherMap);
        } else {
            return "没有对应的业务处理器，请检查！";
        }
    }
}
