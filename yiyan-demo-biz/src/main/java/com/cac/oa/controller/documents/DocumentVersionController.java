package com.cac.oa.controller.documents;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cac.oa.entity.documents.DocumentVersionEntity;
import com.cac.oa.service.documents.DocumentVersionService;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 文档历史版本 Controller
 */
@Api(tags = "OA-文档历史版本记录")
@RestController
@RequestMapping("/api/240/oa/document-version")
@RequiredArgsConstructor
public class DocumentVersionController {

    private final DocumentVersionService versionService;

    @GetMapping("/page")
    @ApiOperation("分页获取指定文档的历史版本记录")
    public Result<PageData<DocumentVersionEntity>> page(
            @RequestParam Long documentId,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        Page<DocumentVersionEntity> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<DocumentVersionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocumentVersionEntity::getDocumentId, documentId);
        wrapper.orderByDesc(DocumentVersionEntity::getCreateTime);

        Page<DocumentVersionEntity> resultPage = versionService.page(page, wrapper);

        PageData<DocumentVersionEntity> pageData = new PageData<>();
        pageData.setList(resultPage.getRecords());
        pageData.setTotal(resultPage.getTotal());
        return Result.ok(pageData);
    }
}
