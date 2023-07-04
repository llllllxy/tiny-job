package org.tinycloud.tinyjob.bean.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class JobInfoAddDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    @NotNull(message = "项目id不能为空")
    private Long projectId;

    /**
     * 主机id
     */
    @NotNull(message = "主机id不能为空")
    private Long hostId;

    /**
     * 任务名称
     */
    @NotEmpty(message = "任务名称不能为空")
    @Length(max = 64, min = 1, message = "任务名称不能超过64个字符")
    private String jobName;

    /**
     * 任务组名
     */
    @NotEmpty(message = "任务组不能为空")
    @Length(max = 64, min = 1, message = "任务组不能超过64个字符")
    private String jobGroup;

    /**
     * 请求方式，get，post，post_json
     */
    @NotEmpty(message = "请求方式不能为空")
    @Length(max = 10, min = 1, message = "请求方式不能超过10个字符")
    private String jobType;

    /**
     * 请求地址
     */
    @NotEmpty(message = "请求地址不能为空")
    @Length(max = 255, min = 1, message = "请求方式不能超过255个字符")
    private String jobUrl;

    /**
     * 请求参数，以json形式保存
     */
    @Length(max = 255, min = 0, message = "请求参数不能超过255个字符")
    private String jobParam;

    /**
     * 请求头信息，以json形式保存
     */
    @Length(max = 255, min = 0, message = "请求头信息不能超过255个字符")
    private String jobHeader;

    /**
     * cron执行表达式
     */
    @NotEmpty(message = "cron执行表达式不能为空")
    @Length(max = 64, min = 1, message = "cron执行表达式不能超过64个字符")
    private String cronExpression;

    /**
     * 计划执行错误策略（0默认 1立即执行 2执行一次 3放弃执行）
     */
    @NotEmpty(message = "计划执行错误策略不能为空")
    @Length(max = 1, min = 1, message = "计划执行错误策略只能为1个字符")
    private String misfirePolicy;

    /**
     * 是否并发执行（0是 1否）
     */
    @NotEmpty(message = "是否并发执行不能为空")
    @Length(max = 1, min = 1, message = "是否并发执行只能为1个字符")
    private String concurrent;

    /**
     * 备注信息
     */
    @Length(max = 500, min = 0, message = "备注信息不能超过500个字符")
    private String remark;

    /**
     * 执行策略（FIRST、LAST、ROUND、RANDOM）
     */
    @NotEmpty(message = "路由策略不能为空")
    @Length(max = 32, min = 1, message = "路由策略不能超过500个字符")
    private String strategy;
}
