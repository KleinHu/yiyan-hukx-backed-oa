package com.cac.demo.controller.operatelog;

import com.cac.demo.convert.operatelog.OperateLogConvert;
import com.cac.demo.entity.operatelog.OperateLogEntity;
import com.cac.demo.service.operatelog.OperateLogService;
import com.cac.demo.vo.operatelog.OperateLogExcel;
import com.cac.demo.vo.operatelog.OperateLogQuery;
import com.cac.demo.vo.operatelog.OperateLogVO;
import com.cac.yiyan.common.constant.PageConstant;
import com.cac.yiyan.common.excel.ErrorReadListener;
import com.cac.yiyan.common.excel.ExcelValidateResult;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.common.util.ExcelUtils;
import com.cac.yiyan.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  Controller
 * </p>
 *
 * @author
 * @since
 */

@Api(tags = "")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/system/operate-log")
public class OperateLogController {

    private final OperateLogService operateLogService;

    private final OperateLogConvert converter = OperateLogConvert.INSTANCE;

    @GetMapping("/{id}")
    @ApiOperation("通过id获得")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:operate-log:query')")
    public Result<OperateLogVO> getOperateLog(@PathVariable("id") Long id) {
        OperateLogEntity operateLog = operateLogService.getOperateLogById(id);
        return Result.ok(converter.convert(operateLog));
    }

    @GetMapping("/list")
    @ApiOperation("获得列表,不分页")
    @PreAuthorize("@ss.hasPermission('system:operate-log:query')")
    public Result<List<OperateLogVO>> getOperateLogList(OperateLogQuery query) {
        List<OperateLogEntity> list = operateLogService.getOperateLogList(query);
        return Result.ok(converter.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = PageConstant.PAGE_NO, value = "当前页码，从1开始", defaultValue = "1", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = PageConstant.PAGE_SIZE, value = "每页显示记录数", defaultValue = "10", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = PageConstant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = PageConstant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @PreAuthorize("@ss.hasPermission('system:operate-log:page')")
    public Result<PageData<OperateLogVO>> page(OperateLogQuery query) {
        PageData<OperateLogEntity> pageData = operateLogService.getOperateLogPage(query);
        return Result.ok(converter.convertPage(pageData));
    }


    @PostMapping
    @ApiOperation("保存")
    @PreAuthorize("@ss.hasPermission('system:operate-log:save')")
    public Result<Long> save(@RequestBody OperateLogVO vo) {
        OperateLogEntity entity = converter.convert(vo);
        Long id = operateLogService.saveOperateLog(entity);
        return Result.ok(id);
    }

    @PutMapping
    @ApiOperation("修改")
    @PreAuthorize("@ss.hasPermission('system:operate-log:update')")
    public Result<Boolean> update(@RequestBody OperateLogVO vo) {
        OperateLogEntity entity = converter.convert(vo);
        Boolean success = operateLogService.updateOperateLog(entity);
        return Result.ok(success);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    @PreAuthorize("@ss.hasPermission('system:operate-log:delete')")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        Boolean success = operateLogService.deleteOperateLogById(id);
        return Result.ok(success);
    }

    @DeleteMapping
    @ApiOperation("批量删除")
    @PreAuthorize("@ss.hasPermission('system:operate-log:delete')")
    public Result<Boolean> delete(@RequestBody Collection<Long> ids) {
        Boolean success = operateLogService.deleteOperateLogByIds(ids);
        return Result.ok(success);
    }

    @GetMapping("/export")
    @ApiOperation("导出 Excel")
    @PreAuthorize("@ss.hasPermission('system:operate-log:export')")
    public void export(OperateLogQuery query, HttpServletResponse response,
        @RequestParam(value = "onlyTemplate", required = false, defaultValue = "false") boolean onlyTemplate) throws Exception {
        List<OperateLogExcel> excelData;
        if (onlyTemplate) {
            // 模板不需要数据
            excelData = Collections.emptyList();
        } else {
            // 通过service获取
            List<OperateLogEntity> list = operateLogService.getOperateLogList(query);
            excelData = converter.convertExcelList(list);
        }
        ExcelUtils.write(response, "操作日志.xlsx", "", OperateLogExcel.class, excelData);
    }

    @PostMapping("/import")
    @ApiOperation("导入 Excel")
    @PreAuthorize("@ss.hasPermission('system:operate-log:import')")
    public void importData(@RequestPart("file") MultipartFile file, HttpServletResponse response) throws Exception {
        ErrorReadListener<OperateLogExcel> listener = new ErrorReadListener<>();
        List<OperateLogExcel> data = ExcelUtils.read(file, OperateLogExcel.class, listener);
        Map<Integer, ExcelValidateResult> validateResultMap = listener.getValidateResultMap();
        List<OperateLogExcel> successList = listener.getSuccessList();
        // 1.在接下来的代码对于导入成功的数据进行处理

        List<OperateLogExcel> errorList = listener.getErrorList();
        // 2.在接下来的代码对于导入失败的数据进行处理（如果需要）

        // 3.生成一个导入结果的excel反给前端
        ExcelUtils.writeError(response, file.getOriginalFilename(), "导入结果", OperateLogExcel.class, data, validateResultMap);
    }

}
