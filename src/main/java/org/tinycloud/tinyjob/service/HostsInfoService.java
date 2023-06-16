package org.tinycloud.tinyjob.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinycloud.tinyjob.bean.entity.THostsItem;
import org.tinycloud.tinyjob.constant.GlobalConstant;
import org.tinycloud.tinyjob.mapper.HostsInfoMapper;
import org.tinycloud.tinyjob.mapper.HostsItemMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HostsInfoService {

    @Autowired
    private HostsInfoMapper hostsInfoMapper;

    @Autowired
    private HostsItemMapper hostsItemMapper;

    public List<String> getAddrs(Long hostId) {
        List<THostsItem> items = this.hostsItemMapper.selectList(Wrappers.<THostsItem>lambdaQuery()
                .eq(THostsItem::getDelFlag, GlobalConstant.NOT_DELETED)
                .eq(THostsItem::getHostId, hostId));
        if (items != null && !items.isEmpty()) {
            return items.stream().map(THostsItem::getHostAddr).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
