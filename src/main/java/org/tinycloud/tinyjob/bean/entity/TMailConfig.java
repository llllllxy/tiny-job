package org.tinycloud.tinyjob.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@TableName("t_mail_config")
public class TMailConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键，内码
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * smtp服务器地址
     */
    @TableField(value = "smtp_address")
    private String smtpAddress;

    /**
     * smtp端口
     */
    @TableField(value = "smtp_port")
    private String smtpPort;

    /**
     * 邮箱账号
     */
    @TableField(value = "email_account")
    private String emailAccount;

    /**
     * 邮箱密码
     */
    @TableField(value = "email_password")
    private String emailPassword;

    /**
     * 收件邮箱地址(多个用逗号隔开)
     */
    @TableField(value = "receive_email")
    private String receiveEmail;

    /**
     * 删除标志（0--未删除1--已删除）
     */
    @TableField(value = "del_flag")
    private Integer delFlag;

    /**
     * 备注描述信息
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private Date createdAt;

    /**
     * 创建时间
     */
    @TableField(value = "updated_at")
    private Date updatedAt;

    /**
     * 创建人-对应t_user.id
     */
    @TableField(value = "created_by")
    private String createdBy;

    /**
     * 更新人-对应t_user.id
     */
    @TableField(value = "updated_by")
    private String updatedBy;
}
