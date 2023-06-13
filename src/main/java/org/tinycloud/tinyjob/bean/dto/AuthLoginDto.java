package org.tinycloud.tinyjob.bean.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <p>
 *     系统登录-DTO
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-01 17:17
 */
@Setter
@Getter
public class AuthLoginDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "登录账户不能为空")
    private String username;

    @NotEmpty(message = "登录密码不能为空")
    private String password;
}
