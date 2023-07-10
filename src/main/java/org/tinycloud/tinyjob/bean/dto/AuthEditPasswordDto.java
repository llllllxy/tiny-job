package org.tinycloud.tinyjob.bean.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <p>
 *     修改密码-DTO
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-01 17:17
 */
@Setter
@Getter
public class AuthEditPasswordDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "旧的密码不能为空")
    private String oldPassword;

    @NotEmpty(message = "新的密码不能为空")
    private String newPassword;

    @NotEmpty(message = "再次输入新的密码不能为空")
    private String againPassword;
}
