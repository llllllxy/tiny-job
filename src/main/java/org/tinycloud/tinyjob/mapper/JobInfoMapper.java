package org.tinycloud.tinyjob.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.tinycloud.tinyjob.bean.dto.JobInfoQueryDto;
import org.tinycloud.tinyjob.bean.entity.TJobInfo;
import org.tinycloud.tinyjob.bean.vo.JobInfoQueryVo;

@Repository
public interface JobInfoMapper extends BaseMapper<TJobInfo> {

    IPage<JobInfoQueryVo> pageList(Page<JobInfoQueryVo> page, @Param("dto") JobInfoQueryDto dto);
}
