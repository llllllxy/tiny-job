package org.tinycloud.tinyjob.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.tinycloud.security.util.AuthUtil;
import org.tinycloud.tinyjob.bean.dto.HostsAddDto;
import org.tinycloud.tinyjob.bean.dto.HostsEditDto;
import org.tinycloud.tinyjob.bean.dto.HostsQueryDto;
import org.tinycloud.tinyjob.bean.entity.THostsInfo;
import org.tinycloud.tinyjob.bean.entity.THostsItem;
import org.tinycloud.tinyjob.bean.vo.HostsQueryVo;
import org.tinycloud.tinyjob.bean.vo.HostsSelectVo;
import org.tinycloud.tinyjob.constant.GlobalConstant;
import org.tinycloud.tinyjob.mapper.HostsInfoMapper;
import org.tinycloud.tinyjob.mapper.HostsItemMapper;
import org.tinycloud.tinyjob.model.PageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HostsInfoService {

    @Autowired
    private HostsInfoMapper hostsInfoMapper;

    @Autowired
    private HostsItemMapper hostsItemMapper;

    /**
     * 根据hostId获取地址列表
     *
     * @param hostId
     * @return
     */
    public List<String> getAddrs(Long hostId) {
        List<THostsItem> items = this.hostsItemMapper.selectList(Wrappers.<THostsItem>lambdaQuery()
                .eq(THostsItem::getDelFlag, GlobalConstant.NOT_DELETED)
                .eq(THostsItem::getHostId, hostId));
        if (items != null && !items.isEmpty()) {
            return items.stream().map(THostsItem::getHostAddr).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }


    /**
     * 根据项目id获取主机列表(服务于前端下拉框)
     *
     * @param projectId
     * @return
     */
    public List<HostsSelectVo> select(Long projectId) {
        List<THostsInfo> items = this.hostsInfoMapper.selectList(Wrappers.<THostsInfo>lambdaQuery()
                .eq(THostsInfo::getDelFlag, GlobalConstant.NOT_DELETED)
                .eq(THostsInfo::getProjectId, projectId));
        if (items != null && !items.isEmpty()) {
            return items.stream().map(item -> {
                HostsSelectVo vo = new HostsSelectVo();
                vo.setHostName(item.getHostName());
                vo.setId(item.getId());
                return vo;
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }


    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    public PageModel<HostsQueryVo> query(HostsQueryDto dto) {
        IPage<HostsQueryVo> pages = this.hostsInfoMapper.pageList(new Page<>(dto.getPageNo(), dto.getPageSize()), dto);
        PageModel<HostsQueryVo> pageModel = new PageModel<>(dto.getPageNo(), dto.getPageSize());
        if (pages != null && !CollectionUtils.isEmpty(pages.getRecords())) {
            pageModel.setTotalPage(pages.getPages());
            pageModel.setTotalCount(pages.getTotal());
            pageModel.setRecords(pages.getRecords());
        }
        return pageModel;
    }


    public Boolean delete(Long id) {
        // 逻辑删除
        LambdaUpdateWrapper<THostsInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(THostsInfo::getId, id);
        wrapper.set(THostsInfo::getDelFlag, GlobalConstant.DELETED);
        wrapper.set(THostsInfo::getUpdatedBy, (String) AuthUtil.getLoginId());
        int rows = this.hostsInfoMapper.update(null, wrapper);
        return rows > 0;
    }

    @Transactional
    public Boolean add(HostsAddDto dto) {
        THostsInfo hostInfo = new THostsInfo();
        hostInfo.setHostName(dto.getHostName());
        hostInfo.setProjectId(dto.getProjectId());
        hostInfo.setRemark(dto.getRemark());
        hostInfo.setDelFlag(GlobalConstant.NOT_DELETED);
        hostInfo.setCreatedBy((String) AuthUtil.getLoginId());
        int rows = this.hostsInfoMapper.insert(hostInfo);

        String hostAddrs = dto.getHostAddrs();
        if (StringUtils.hasText(hostAddrs)) {
            String[] hostArray = hostAddrs.split(",");
            for (String host : hostArray) {
                THostsItem hostsItem = new THostsItem();
                hostsItem.setHostId(hostInfo.getId());
                hostsItem.setHostAddr(host);
                hostsItem.setDelFlag(GlobalConstant.NOT_DELETED);
                hostsItem.setCreatedBy((String) AuthUtil.getLoginId());
                this.hostsItemMapper.insert(hostsItem);
            }
        }
        return rows > 0;
    }

    @Transactional
    public Boolean edit(HostsEditDto dto) {
        LambdaUpdateWrapper<THostsInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(THostsInfo::getId, dto.getId());
        wrapper.set(THostsInfo::getHostName, dto.getHostName());
        wrapper.set(THostsInfo::getProjectId, dto.getProjectId());
        wrapper.set(THostsInfo::getRemark, dto.getRemark());
        wrapper.set(THostsInfo::getDelFlag, GlobalConstant.DELETED);
        wrapper.set(THostsInfo::getUpdatedBy, (String) AuthUtil.getLoginId());
        int rows = this.hostsInfoMapper.update(null, wrapper);

        this.hostsItemMapper.delete(Wrappers.<THostsItem>lambdaUpdate().eq(THostsItem::getHostId, dto.getId()));
        String hostAddrs = dto.getHostAddrs();
        if (StringUtils.hasText(hostAddrs)) {
            String[] hostArray = hostAddrs.split(",");
            for (String host : hostArray) {
                THostsItem hostsItem = new THostsItem();
                hostsItem.setHostId(dto.getId());
                hostsItem.setHostAddr(host);
                hostsItem.setDelFlag(GlobalConstant.NOT_DELETED);
                hostsItem.setCreatedBy((String) AuthUtil.getLoginId());
                this.hostsItemMapper.insert(hostsItem);
            }
        }

        return rows > 0;
    }
}
