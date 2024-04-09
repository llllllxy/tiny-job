package org.tinycloud.tinyjob.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.tinycloud.tinyjob.bean.dto.AuthEditInfoDto;
import org.tinycloud.tinyjob.bean.dto.AuthEditPasswordDto;
import org.tinycloud.tinyjob.bean.dto.AuthLoginDto;
import org.tinycloud.tinyjob.bean.entity.TUser;
import org.tinycloud.tinyjob.bean.vo.UserInfoVo;
import org.tinycloud.tinyjob.constant.ApiErrorCode;
import org.tinycloud.tinyjob.constant.GlobalConstant;
import org.tinycloud.tinyjob.exception.BusinessException;
import org.tinycloud.tinyjob.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinycloud.tinyjob.utils.AuthUtils;
import org.tinycloud.tinyjob.utils.BeanConvertUtils;
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


    public UserInfoVo getUserInfo() {
        String username = (String) AuthUtils.getLoginId();
        TUser entity = this.userMapper.selectOne(
                Wrappers.<TUser>lambdaQuery().eq(TUser::getUsername, username)
                        .eq(TUser::getDelFlag, GlobalConstant.NOT_DELETED));
        if (entity == null) {
            return null;
        } else {
            return BeanConvertUtils.convertTo(entity, UserInfoVo::new);
        }
    }


    public boolean editPassword(AuthEditPasswordDto dto) {
        if (!dto.getNewPassword().equals(dto.getAgainPassword())) {
            throw new BusinessException(ApiErrorCode.THE_NEWPASSWORD_ENTERED_TWICE_DOES_NOT_MATCH.getCode(),
                    ApiErrorCode.THE_NEWPASSWORD_ENTERED_TWICE_DOES_NOT_MATCH.getDesc());
        }
        String username = (String) AuthUtils.getLoginId();
        TUser entity = this.userMapper.selectOne(
                Wrappers.<TUser>lambdaQuery().eq(TUser::getUsername, username)
                        .eq(TUser::getDelFlag, GlobalConstant.NOT_DELETED));
        String encodedPassword = entity.getPassword();
        boolean checkResult = BCrypt.checkpw(dto.getOldPassword() + SALT, encodedPassword);
        // 原始密码错误
        if (!checkResult) {
            throw new BusinessException(ApiErrorCode.THE_ORIGINAL_PASSWORD_IS_WRONG.getCode(),
                    ApiErrorCode.THE_ORIGINAL_PASSWORD_IS_WRONG.getDesc());
        }
        String encodedNewPassword = BCrypt.hashpw(dto.getNewPassword() + SALT, BCrypt.gensalt());
        LambdaUpdateWrapper<TUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TUser::getUsername, username);
        wrapper.set(TUser::getPassword, encodedNewPassword);
        int rows = this.userMapper.update(null, wrapper);
        return rows > 0;
    }


    public boolean setting(AuthEditInfoDto dto) {
        LambdaUpdateWrapper<TUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TUser::getUsername, dto.getUsername());
        wrapper.set(TUser::getEmail, dto.getEmail());
        wrapper.set(TUser::getNickname, dto.getNickname());
        wrapper.set(TUser::getPhone, dto.getPhone());
        int rows = this.userMapper.update(null, wrapper);
        return rows > 0;
    }
}
