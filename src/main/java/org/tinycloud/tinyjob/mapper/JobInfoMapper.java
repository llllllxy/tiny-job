package org.tinycloud.tinyjob.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.tinycloud.tinyjob.bean.entity.TJobInfo;

@Repository
public interface JobInfoMapper extends BaseMapper<TJobInfo> {
}
