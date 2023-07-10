package org.tinycloud.tinyjob.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.tinycloud.tinyjob.bean.entity.TJobLog;

import java.util.List;
import java.util.Map;


@Repository
public interface JobLogMapper extends BaseMapper<TJobLog> {
   List<Map<String, Object>> queryLogReport(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
