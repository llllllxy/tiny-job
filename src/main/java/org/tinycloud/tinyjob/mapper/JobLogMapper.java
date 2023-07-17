package org.tinycloud.tinyjob.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.tinycloud.tinyjob.bean.dto.JobLogQueryDto;
import org.tinycloud.tinyjob.bean.entity.TJobLog;
import org.tinycloud.tinyjob.bean.vo.JobLogQueryVo;

import java.util.List;
import java.util.Map;


@Repository
public interface JobLogMapper extends BaseMapper<TJobLog> {
   List<Map<String, Object>> queryLogReport(@Param("startDate") String startDate, @Param("endDate") String endDate);

   IPage<JobLogQueryVo> pageList(Page<JobLogQueryVo> page, JobLogQueryDto dto);
}
