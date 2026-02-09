package com.cac.oa.controller.supplies;

import com.cac.oa.convert.supplies.SuppliesItemConvert;
import com.cac.oa.entity.supplies.SuppliesItemEntity;
import com.cac.oa.service.supplies.ISuppliesItemService;
import com.cac.oa.vo.supplies.InventoryChangeRequest;
import com.cac.oa.vo.supplies.SuppliesItemVO;
import com.cac.oa.vo.supplies.SuppliesQuery;
import com.cac.yiyan.common.constant.PageConstant;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.cac.oa.vo.supplies.SuppliesRecordQuery;
import com.cac.oa.vo.supplies.SuppliesRecordVO;

/**
 * 办公用品档案 Controller
 */
@Api(tags = "办公用品-物品档案")
@RestController
@RequestMapping("/api/240/oa/supplies/item")
@RequiredArgsConstructor
public class SuppliesItemController {

    private final ISuppliesItemService itemService;
    private final SuppliesItemConvert converter = SuppliesItemConvert.INSTANCE;

    @GetMapping("/page")
    @ApiOperation("分页查询物品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = PageConstant.PAGE_NO, value = "当前页码，从1开始", defaultValue = "1", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = PageConstant.PAGE_SIZE, value = "每页显示记录数", defaultValue = "10", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = PageConstant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = PageConstant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    public Result<PageData<SuppliesItemVO>> page(SuppliesQuery query) {
        return Result.ok(itemService.getPage(query));
    }

    @PostMapping
    @ApiOperation("新增物品")
    public Result<Boolean> save(@RequestBody SuppliesItemVO vo) {
        SuppliesItemEntity entity = converter.convert(vo);
        return Result.ok(itemService.save(entity));
    }

    @PutMapping
    @ApiOperation("修改物品")
    public Result<Boolean> update(@RequestBody SuppliesItemVO vo) {
        SuppliesItemEntity entity = converter.convert(vo);
        return Result.ok(itemService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除物品")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.ok(itemService.removeById(id));
    }

    @PostMapping("/inventory/change")
    @ApiOperation("变更库存 (入库/盘点等)")
    public Result<Void> changeInventory(@RequestBody InventoryChangeRequest request) {
        itemService.changeInventory(request);
        return Result.ok();
    }

    @GetMapping("/record/page")
    @ApiOperation("分页查询库存流水")
    @ApiImplicitParams({
            @ApiImplicitParam(name = PageConstant.PAGE_NO, value = "当前页码，从1开始", defaultValue = "1", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = PageConstant.PAGE_SIZE, value = "每页显示记录数", defaultValue = "10", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = PageConstant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = PageConstant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    public Result<PageData<SuppliesRecordVO>> recordPage(SuppliesRecordQuery query) {
        return Result.ok(itemService.getRecordPage(query));
    }
}
