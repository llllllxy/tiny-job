package org.tinycloud.tinyjob.bean.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JobInfoAddDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 主机id
     */
    private Long hostId;

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
     * cron执行表达式
     */
    private String cronExpression;

    /**
     * 计划执行错误策略（1立即执行 2执行一次 3放弃执行）
     */
    private String misfirePolicy;

    /**
     * 是否并发执行（0允许 1禁止）
     */
    private String concurrent;

    /**
     * 状态（0正常 1暂停）
     */
    private String status;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 执行策略（FIRST、LAST、ROUND、RANDOM）
     */
    private String strategy;
}
