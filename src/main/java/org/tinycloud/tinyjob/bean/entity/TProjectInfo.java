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
 * 系统项目实体类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-01 14:54
 */
@Getter
@Setter
@TableName("t_project_log")
public class TProjectInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 日志id（主键id）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务id
     */
    @TableField("project_name")
    private Long projectName;

    /**
     * 删除标志（0--未删除1--已删除）
     */
    @TableField("del_flag")
    private int delFlag;

    /**
     * 创建者
     */
    @TableField("created_by")
    private String createdBy;

    /**
     * 更新人
     */
    @TableField("updated_by")
    private String updatedBy;

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
}
