package org.tinycloud.tinyjob.bean.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class JobInfoQueryVo implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 任务id（主键id）
     */
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 主机id
     */
    private Long hostId;

    /**
     * 主机名称
     */
    private String hostName;

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
     * 触发器类型（CRON、SIMPLE）
     */
    private String jobTrigger;

    /**
     * 简单任务的重复间隔时间（以秒为单位）
     */
    private Integer intervalSeconds;

    /**
     * cron执行表达式
     */
    private String cronExpression;

    /**
     * 调度过期策略（1立即执行 2执行一次 3放弃执行）
     */
    private String misfirePolicy;

    /**
     * 是否并发执行（0允许 1禁止）
     */
    private String concurrent;

    /**
     * 下次执行时间
     */
    private Date nextExecuteTime;

    /**
     * 状态（0正常 1暂停）
     */
    private String status;

    /**
     * 删除标志（0--未删除1--已删除）
     */
    private int delFlag;

    /**
     * 创建者
     */
    private String createdBy;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 路由策略（FIRST、LAST、ROUND、RANDOM）
     */
    private String strategy;

    /**
     * 失败重试次数
     */
    private Integer failRetryTimes;

    /**
     * 任务超时时间（单位毫秒）
     */
    private Integer executorTimeout;
}
