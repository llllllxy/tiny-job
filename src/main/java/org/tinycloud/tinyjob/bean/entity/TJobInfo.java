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
 * 系统任务实体类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-01 14:54
 */
@Getter
@Setter
@TableName("t_job_info")
public class TJobInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 任务id（主键id）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 项目id
     */
    @TableField("project_id")
    private Long projectId;

    /**
     * 主机id
     */
    @TableField("host_id")
    private Long hostId;

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
     * cron执行表达式
     */
    @TableField("cron_expression")
    private String cronExpression;

    /**
     * 计划执行错误策略（1立即执行 2执行一次 3放弃执行）
     */
    @TableField("misfire_policy")
    private String misfirePolicy;

    /**
     * 是否并发执行（0允许 1禁止）
     */
    @TableField("concurrent")
    private String concurrent;

    /**
     * 下次执行时间
     */
    @TableField("next_execute_time")
    private Date nextExecuteTime;

    /**
     * 状态（0正常 1暂停）
     */
    @TableField("status")
    private String status;

    /**
     * 删除标志（0--未删除1--已删除）
     */
    @TableField("del_flag")
    private int delFlag;

    /**
     * 创建者
     */
    @TableField("created_by")
    private Long createdBy;

    /**
     * 更新人
     */
    @TableField("updated_by")
    private Long updatedBy;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private Date updatedAt;

    /**
     * 备注信息
     */
    @TableField("remark")
    private String remark;

    /**
     * 执行策略（FIRST、LAST、ROUND、RANDOM）
     */
    @TableField("strategy")
    private String strategy;
}
