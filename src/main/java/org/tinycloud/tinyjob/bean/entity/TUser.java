package org.tinycloud.tinyjob.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 系统用户实体类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-01 14:54
 */
@Getter
@Setter
@TableName("t_user")
public class TUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 状态（0--正常 1--冻结）
     */
    @TableField("status")
    private String status;

    /**
     * 邮箱
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 删除标志（0--未删除1--已删除）
     */
    @TableField("del_flag")
    private String delFlag;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private String createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private String updatedAt;
}
