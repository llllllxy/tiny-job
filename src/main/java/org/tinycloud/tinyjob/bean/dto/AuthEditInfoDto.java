package org.tinycloud.tinyjob.bean.dto;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <p>
 *     修改个人信息-DTO
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-01 17:17
 */
@Setter
@Getter
public class AuthEditInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @Length(max = 32, min = 0, message = "昵称不能超过32个字符")
    private String nickname;

    @Length(max = 32, min = 0, message = "联系方式不能超过32个字符")
    private String phone;

    @Length(max = 64, min = 0, message = "电子邮件不能超过64个字符")
    private String email;
}
