package org.tinycloud.tinyjob.bean.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统任务执行结果
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-01 14:54
 */
@Getter
@Setter
public class JobResult {

    /**
     * 请求返回的结果
     */
    private String returnInfo;

    /**
     * 最终选择的路由
     */
    private String routeJobUrl;

}
