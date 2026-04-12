package com.cac.oa.controller.duty;

import com.cac.oa.service.duty.DutyGroupService;
import com.cac.oa.vo.duty.DutyGroupSaveReqVO;
import com.cac.oa.vo.duty.DutyGroupVO;
import com.cac.yiyan.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "OA-值班分组管理")
@RestController
@RequestMapping("/api/240/oa/duty-group")
@Validated
public class DutyGroupController {

    @Resource
    private DutyGroupService dutyGroupService;

    @GetMapping("/tree")
    @ApiOperation("获取分组树形列表")
    public Result<List<DutyGroupVO>> getGroupTree() {
        return Result.ok(dutyGroupService.getGroupTree());
    }

    @PostMapping
    @ApiOperation("创建分组")
    public Result<Long> createGroup(@Valid @RequestBody DutyGroupSaveReqVO vo) {
        return Result.ok(dutyGroupService.saveGroup(vo));
    }

    @PutMapping
    @ApiOperation("更新分组")
    public Result<Long> updateGroup(@Valid @RequestBody DutyGroupSaveReqVO vo) {
        return Result.ok(dutyGroupService.saveGroup(vo));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除分组")
    public Result<Boolean> deleteGroup(@PathVariable("id") Long id) {
        dutyGroupService.deleteGroup(id);
        return Result.ok(true);
    }
}
