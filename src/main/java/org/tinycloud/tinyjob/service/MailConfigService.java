package org.tinycloud.tinyjob.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinycloud.security.util.AuthUtil;
import org.tinycloud.tinyjob.bean.dto.MailConfigEditDto;
import org.tinycloud.tinyjob.bean.entity.TMailConfig;
import org.tinycloud.tinyjob.bean.vo.MailConfigVo;
import org.tinycloud.tinyjob.constant.GlobalConstant;
import org.tinycloud.tinyjob.mapper.MailConfigMapper;
import org.tinycloud.tinyjob.utils.BeanConvertUtils;

@Service
public class MailConfigService {

    @Autowired
    private MailConfigMapper mailConfigMapper;

    public MailConfigVo detail() {
        TMailConfig mailConfig = mailConfigMapper.selectOne((Wrappers.<TMailConfig>lambdaQuery()
                .eq(TMailConfig::getDelFlag, GlobalConstant.NOT_DELETED)));
        if (mailConfig == null) {
            return null;
        } else {
          return BeanConvertUtils.convertTo(mailConfig, MailConfigVo::new) ;
        }
    }

    public Boolean edit(MailConfigEditDto dto) {
        TMailConfig mailConfig = mailConfigMapper.selectOne((Wrappers.<TMailConfig>lambdaQuery()
                .eq(TMailConfig::getDelFlag, GlobalConstant.NOT_DELETED)));
        int num = 0;
        if (mailConfig != null) { // 更新
            long id = mailConfig.getId();
            LambdaUpdateWrapper<TMailConfig> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(TMailConfig::getId, id);
            wrapper.set(TMailConfig::getSmtpAddress, dto.getSmtpAddress());
            wrapper.set(TMailConfig::getSmtpPort, dto.getSmtpPort());
            wrapper.set(TMailConfig::getEmailAccount, dto.getEmailAccount());
            wrapper.set(TMailConfig::getEmailPassword, dto.getEmailPassword());
            wrapper.set(TMailConfig::getReceiveEmail, dto.getReceiveEmail());
            wrapper.set(TMailConfig::getUpdatedBy, (String) AuthUtil.getLoginId());

            num = mailConfigMapper.update(null, wrapper);
        } else { // 插入
            mailConfig = new TMailConfig();
            mailConfig.setSmtpAddress(dto.getSmtpAddress());
            mailConfig.setSmtpPort(dto.getSmtpPort());
            mailConfig.setEmailAccount(dto.getEmailAccount());
            mailConfig.setEmailPassword(dto.getEmailPassword());
            mailConfig.setReceiveEmail(dto.getReceiveEmail());
            mailConfig.setCreatedBy((String) AuthUtil.getLoginId());
            mailConfig.setDelFlag(GlobalConstant.NOT_DELETED);
            num = mailConfigMapper.insert(mailConfig);
        }
        return num > 0;
    }

}
