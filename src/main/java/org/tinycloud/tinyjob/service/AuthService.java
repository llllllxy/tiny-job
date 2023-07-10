package org.tinycloud.tinyjob.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.tinycloud.tinyjob.bean.dto.AuthLoginDto;
import org.tinycloud.tinyjob.bean.entity.TUser;
import org.tinycloud.tinyjob.constant.ApiErrorCode;
import org.tinycloud.tinyjob.constant.GlobalConstant;
import org.tinycloud.tinyjob.exception.BusinessException;
import org.tinycloud.tinyjob.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinycloud.tinyjob.utils.secure.BCrypt;

import java.util.Objects;

/**
 * <p>
 * 会话服务层
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-02 14:56
 */
@Service
public class AuthService {

    private static final String SALT = "323@#@$1234da323@#@$1234da";

    @Autowired
    private UserMapper userMapper;

    public void authentication(AuthLoginDto authLoginDto) {
        String username = authLoginDto.getUsername();
        String password = authLoginDto.getPassword();

        TUser entity = this.userMapper.selectOne(
                Wrappers.<TUser>lambdaQuery().eq(TUser::getUsername, username)
                        .eq(TUser::getDelFlag, GlobalConstant.NOT_DELETED));
        // 用户不存在
        if (Objects.isNull(entity)) {
            throw new BusinessException(ApiErrorCode.USERNAME_OR_PASSWORD_MISMATCH.getCode(),
                    ApiErrorCode.USERNAME_OR_PASSWORD_MISMATCH.getDesc());
        }
        // 密码错误
        String encodedPassword = entity.getPassword();
        boolean checkResult = BCrypt.checkpw(password + SALT, encodedPassword);
        if (!checkResult) {
            throw new BusinessException(ApiErrorCode.USERNAME_OR_PASSWORD_MISMATCH.getCode(),
                    ApiErrorCode.USERNAME_OR_PASSWORD_MISMATCH.getDesc());
        }
        // 用户已被停用
        if (entity.getStatus().equals(GlobalConstant.DISABLED)) {
            throw new BusinessException(ApiErrorCode.USER_IS_DISABLED.getCode(),
                    ApiErrorCode.USER_IS_DISABLED.getDesc());
        }
    }

}
