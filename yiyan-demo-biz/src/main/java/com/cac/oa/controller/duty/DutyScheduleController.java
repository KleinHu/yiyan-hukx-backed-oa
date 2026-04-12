package com.cac.oa.controller.duty;

import com.cac.oa.service.duty.DutyScheduleService;
import com.cac.oa.vo.duty.DutyScheduleQueryVO;
import com.cac.oa.vo.duty.DutyScheduleSaveReqVO;
import com.cac.oa.vo.duty.DutyScheduleVO;
import com.cac.yiyan.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "OA-值班排班管理")
@RestController
@RequestMapping("/api/240/oa/duty-schedule")
@Validated
public class DutyScheduleController {

    @Resource
    private DutyScheduleService dutyScheduleService;

    @GetMapping("/list")
    @ApiOperation("获取排班列表")
    public Result<List<DutyScheduleVO>> getScheduleList(DutyScheduleQueryVO query) {
        return Result.ok(dutyScheduleService.getScheduleList(query));
    }

    @PostMapping
    @ApiOperation("创建排班")
    public Result<Long> createSchedule(@Valid @RequestBody DutyScheduleSaveReqVO vo) {
        return Result.ok(dutyScheduleService.saveSchedule(vo));
    }

    @PutMapping
    @ApiOperation("更新排班")
    public Result<Long> updateSchedule(@Valid @RequestBody DutyScheduleSaveReqVO vo) {
        return Result.ok(dutyScheduleService.saveSchedule(vo));
    }

    @PostMapping("/batch")
    @ApiOperation("批量保存排班")
    public Result<Boolean> batchSaveSchedule(@Valid @RequestBody List<DutyScheduleSaveReqVO> vos) {
        dutyScheduleService.batchSaveSchedule(vos);
        return Result.ok(true);
    }

    @DeleteMapping("/batch")
    @ApiOperation("批量删除排班")
    public Result<Boolean> deleteScheduleBatch(@RequestBody List<Long> ids) {
        dutyScheduleService.deleteScheduleBatch(ids);
        return Result.ok(true);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除排班")
    public Result<Boolean> deleteSchedule(@PathVariable("id") Long id) {
        dutyScheduleService.deleteSchedule(id);
        return Result.ok(true);
    }
}
