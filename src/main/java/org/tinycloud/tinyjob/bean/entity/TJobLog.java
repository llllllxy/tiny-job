package org.tinycloud.tinyjob.bean.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统任务执行记录实体类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-01 14:54
 */
@Getter
@Setter
@TableName("t_job_log")
public class TJobLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 日志id（主键id）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务id
     */
    @TableField("job_id")
    private Long jobId;

    /**
     * 任务名称
     */
    @TableField("job_name")
    private String jobName;

    /**
     * 任务组名
     */
    @TableField("job_group")
    private String jobGroup;

    /**
     * 请求方式，get，post，post_json
     */
    @TableField("job_type")
    private String jobType;

    /**
     * 请求地址
     */
    @TableField("job_url")
    private String jobUrl;

    /**
     * 请求参数，以json形式保存
     */
    @TableField("job_param")
    private String jobParam;

    /**
     * 请求头信息，以json形式保存
     */
    @TableField("job_header")
    private String jobHeader;

    /**
     * 请求结果
     */
    @TableField("return_info")
    private String returnInfo;

    /**
     * 状态（0正常 1暂停）
     */
    @TableField("status")
    private String status;

    /**
     * 异常信息
     */
    @TableField("exception_info")
    private String exceptionInfo;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;

    /**
     * 执行时间
     */
    @TableField("execute_at")
    private Date executeAt;

    /**
     * 结束时间
     */
    @TableField("end_at")
    private Date endAt;

    /**
     * 执行耗时
     */
    @TableField("consuming")
    private Integer consuming;
}
