package org.tinycloud.tinyjob.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.tinycloud.tinyjob.bean.dto.HostsQueryDto;
import org.tinycloud.tinyjob.bean.entity.THostsInfo;
import org.tinycloud.tinyjob.bean.vo.HostsQueryVo;

@Repository
public interface HostsInfoMapper extends BaseMapper<THostsInfo> {

    IPage<HostsQueryVo> pageList(Page<HostsQueryVo> page, @Param("dto") HostsQueryDto dto);


}
