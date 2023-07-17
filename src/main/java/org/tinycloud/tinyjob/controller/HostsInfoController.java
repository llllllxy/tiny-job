package org.tinycloud.tinyjob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tinycloud.tinyjob.bean.dto.HostsAddDto;
import org.tinycloud.tinyjob.bean.dto.HostsEditDto;
import org.tinycloud.tinyjob.bean.dto.HostsQueryDto;
import org.tinycloud.tinyjob.bean.vo.HostsQueryVo;
import org.tinycloud.tinyjob.bean.vo.HostsSelectVo;
import org.tinycloud.tinyjob.model.ApiResult;
import org.tinycloud.tinyjob.model.PageModel;
import org.tinycloud.tinyjob.service.HostsInfoService;

import java.util.List;

@RestController
@RequestMapping("/hosts")
public class HostsInfoController {

    @Autowired
    private HostsInfoService hostsInfoService;

    /**
     * 根据项目id获取主机列表(服务于前端下拉框)
     *
     * @return
     */
    @GetMapping("/select")
    public ApiResult<List<HostsSelectVo>> select(@RequestParam("projectId") Long projectId) {
        return ApiResult.success(hostsInfoService.select(projectId), "获取成功!");
    }

    /**
     * 分页查询主机
     *
     * @return
     */
    @PostMapping("/query")
    public ApiResult<PageModel<HostsQueryVo>> query(@RequestBody HostsQueryDto dto) {
        return ApiResult.success(hostsInfoService.query(dto), "查询成功!");
    }

    /**
     * 删除主机
     *
     * @return
     */
    @GetMapping("/delete")
    public ApiResult<?> delete(@RequestParam("id") Long id) {
        return ApiResult.success(hostsInfoService.delete(id), "删除成功!");
    }

    /**
     * 新增主机
     *
     * @return
     */
    @PostMapping("/add")
    public ApiResult<Boolean> add(@Validated @RequestBody HostsAddDto dto) {
        return ApiResult.success(hostsInfoService.add(dto), "新增主机成功!");
    }

    /**
     * 修改主机
     *
     * @return
     */
    @PostMapping("/edit")
    public ApiResult<Boolean> edit(@Validated @RequestBody HostsEditDto dto) {
        return ApiResult.success(hostsInfoService.edit(dto), "修改主机成功!");
    }
}
