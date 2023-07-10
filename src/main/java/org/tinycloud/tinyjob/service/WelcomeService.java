package org.tinycloud.tinyjob.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinycloud.tinyjob.bean.entity.THostsInfo;
import org.tinycloud.tinyjob.bean.entity.TJobInfo;
import org.tinycloud.tinyjob.bean.entity.TJobLog;
import org.tinycloud.tinyjob.bean.entity.TProjectInfo;
import org.tinycloud.tinyjob.constant.GlobalConstant;
import org.tinycloud.tinyjob.mapper.HostsInfoMapper;
import org.tinycloud.tinyjob.mapper.JobInfoMapper;
import org.tinycloud.tinyjob.mapper.JobLogMapper;
import org.tinycloud.tinyjob.mapper.ProjectInfoMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class WelcomeService {

    @Autowired
    private ProjectInfoMapper projectInfoMapper;

    @Autowired
    private HostsInfoMapper hostsInfoMapper;

    @Autowired
    private JobLogMapper jobLogMapper;

    @Autowired
    private JobInfoMapper jobInfoMapper;

    public Map<String, Long> quantity() {
        Long jobQuantity = jobInfoMapper.selectCount(Wrappers.<TJobInfo>lambdaQuery()
                .eq(TJobInfo::getDelFlag, GlobalConstant.NOT_DELETED));

        Long jobEnableQuantity = jobInfoMapper.selectCount(Wrappers.<TJobInfo>lambdaQuery()
                .eq(TJobInfo::getDelFlag, GlobalConstant.NOT_DELETED)
                .eq(TJobInfo::getStatus, GlobalConstant.ENABLED));

        Long projectQuantity = projectInfoMapper.selectCount(Wrappers.<TProjectInfo>lambdaQuery()
                .eq(TProjectInfo::getDelFlag, GlobalConstant.NOT_DELETED));

        Long hostQuantity = hostsInfoMapper.selectCount(Wrappers.<THostsInfo>lambdaQuery()
                .eq(THostsInfo::getDelFlag, GlobalConstant.NOT_DELETED));

        Long logQuantity = jobLogMapper.selectCount(null);

        Long successfulQuantity = jobLogMapper.selectCount(Wrappers.<TJobLog>lambdaQuery()
                .eq(TJobLog::getStatus, GlobalConstant.ENABLED));

        Long failedQuantity = jobLogMapper.selectCount(Wrappers.<TJobLog>lambdaQuery()
                .eq(TJobLog::getStatus, GlobalConstant.DISABLED));

        return new HashMap<String, Long>() {{
            put("jobQuantity", jobQuantity);
            put("jobEnableQuantity", jobEnableQuantity);
            put("projectQuantity", projectQuantity);
            put("hostQuantity", hostQuantity);

            put("logQuantity", logQuantity);
            put("successfulQuantity", successfulQuantity);
            put("failedQuantity", failedQuantity);
        }};
    }


    public Map<String, Object> chartsInfo() {
        List<String> dateList = new ArrayList<>();

        // 获取当前日期
        LocalDate now = LocalDate.now();
        String today = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(now);
        dateList.add(today);

        // 获取前七天的日期
        LocalDate previousDay = now;
        for (int i = 0; i < 6; i++) {
            previousDay = previousDay.minusDays(1);
            dateList.add(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(previousDay));
        }
        String startDay = dateList.get(dateList.size() - 1);
        List<Map<String, Object>> logData = jobLogMapper.queryLogReport(startDay, today);

        List<Long> countList = new ArrayList<>();
        List<Long> successCountList = new ArrayList<>();
        List<Long> failedCountList = new ArrayList<>();

        for (Map<String, Object> item : logData) {
            Long count = new BigDecimal(item.get("allCount").toString()).longValue();
            Long successCount = new BigDecimal(item.get("successCount").toString()).longValue();
            Long failCount = new BigDecimal(item.get("failCount").toString()).longValue();

            countList.add(count);
            successCountList.add(successCount);
            failedCountList.add(failCount);
        }

        return new HashMap<String, Object>() {{
            put("dateList", dateList);
            put("countList", countList);
            put("successCountList", successCountList);
            put("failedCountList", failedCountList);
        }};
    }
}
