package org.tinycloud.tinyjob.bean.vo;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class JobLogQueryVo {

    /**
     * 任务日志id（主键id）
     */
    private Long id;


    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 主机名称
     */
    private String hostName;

    /**
     * 任务id
     */
    private Long jobId;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 请求方式，get，post，post_json
     */
    private String jobType;

    /**
     * 请求地址
     */
    private String jobUrl;

    /**
     * 请求参数，以json形式保存
     */
    private String jobParam;

    /**
     * 请求头信息，以json形式保存
     */
    private String jobHeader;

    /**
     * 请求结果
     */
    private String returnInfo;

    /**
     * 状态（0正常 1暂停）
     */
    private String status;

    /**
     * 异常信息
     */
    private String exceptionInfo;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 执行时间
     */
    private Date executeAt;

    /**
     * 结束时间
     */
    private Date endAt;

    /**
     * 执行耗时
     */
    private Integer consuming;
}
