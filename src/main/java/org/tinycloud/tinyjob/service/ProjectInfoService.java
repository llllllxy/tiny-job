package org.tinycloud.tinyjob.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.tinycloud.tinyjob.bean.dto.ProjectQueryDto;
import org.tinycloud.tinyjob.bean.entity.TProjectInfo;
import org.tinycloud.tinyjob.bean.vo.ProjectQueryVo;
import org.tinycloud.tinyjob.bean.vo.ProjectSelectVo;
import org.tinycloud.tinyjob.constant.GlobalConstant;
import org.tinycloud.tinyjob.mapper.ProjectInfoMapper;
import org.tinycloud.tinyjob.model.PageModel;
import org.tinycloud.tinyjob.utils.BeanConvertUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectInfoService {

    @Autowired
    private ProjectInfoMapper projectInfoMapper;


    public List<ProjectSelectVo> select() {
        List<TProjectInfo> lists = this.projectInfoMapper.selectList(Wrappers.<TProjectInfo>lambdaQuery()
                .select(TProjectInfo::getId, TProjectInfo::getProjectName)
                .eq(TProjectInfo::getDelFlag, GlobalConstant.NOT_DELETED));
        return BeanConvertUtils.convertListTo(lists, ProjectSelectVo::new);
    }


    public PageModel<ProjectQueryVo>  query(ProjectQueryDto queryParam) {
        PageModel<ProjectQueryVo> responsePage = new PageModel<>(queryParam.getPageNo(), queryParam.getPageSize());

        LambdaQueryWrapper<TProjectInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(queryParam.getProjectName()),
                TProjectInfo::getProjectName, queryParam.getProjectName());

        Page<TProjectInfo> logPage = this.projectInfoMapper.selectPage(Page.of(queryParam.getPageNo(), queryParam.getPageSize()), queryWrapper);

        if (logPage != null && !CollectionUtils.isEmpty(logPage.getRecords())) {
            responsePage.setTotalPage(logPage.getPages());
            responsePage.setTotalCount(logPage.getTotal());
            responsePage.setRecords(logPage.getRecords().stream().map(x -> {
                ProjectQueryVo vo = new ProjectQueryVo();
                BeanUtils.copyProperties(x, vo);
                return vo;
            }).collect(Collectors.toList()));
        }

        return responsePage;
    }

}
