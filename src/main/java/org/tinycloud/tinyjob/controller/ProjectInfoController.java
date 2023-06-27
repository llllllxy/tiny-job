package org.tinycloud.tinyjob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tinycloud.tinyjob.bean.dto.ProjectAddDto;
import org.tinycloud.tinyjob.bean.dto.ProjectEditDto;
import org.tinycloud.tinyjob.bean.dto.ProjectQueryDto;
import org.tinycloud.tinyjob.bean.vo.ProjectQueryVo;
import org.tinycloud.tinyjob.bean.vo.ProjectSelectVo;
import org.tinycloud.tinyjob.model.ApiResult;
import org.tinycloud.tinyjob.model.PageModel;
import org.tinycloud.tinyjob.service.ProjectInfoService;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectInfoController {

    @Autowired
    private ProjectInfoService projectInfoService;

    /**
     * 获取项目列表(服务于前端下拉框)
     *
     * @return
     */
    @GetMapping("/select")
    public ApiResult<List<ProjectSelectVo>> select()  {
        return ApiResult.success(projectInfoService.select(), "查询成功!");
    }


    /**
     * 分页查询项目
     *
     * @return
     */
    @PostMapping("/query")
    public ApiResult<PageModel<ProjectQueryVo>> query(@RequestBody ProjectQueryDto dto) {
        return ApiResult.success(projectInfoService.query(dto), "查询成功!");
    }


    /**
     * 新增项目
     *
     * @return
     */
    @PostMapping("/add")
    public ApiResult<Boolean> add(@Validated @RequestBody ProjectAddDto dto) {
        return ApiResult.success(projectInfoService.add(dto), "新增成功!");
    }


    /**
     * 修改项目
     *
     * @return
     */
    @PostMapping("/edit")
    public ApiResult<Boolean> edit(@Validated @RequestBody ProjectEditDto dto) {
        return ApiResult.success(projectInfoService.edit(dto), "修改成功!");
    }

    /**
     * 删除项目
     *
     * @return
     */
    @GetMapping("/delete")
    public ApiResult<?> delete(@RequestParam("id") Long id) {
        return ApiResult.success(projectInfoService.delete(id), "删除成功!");
    }
}
