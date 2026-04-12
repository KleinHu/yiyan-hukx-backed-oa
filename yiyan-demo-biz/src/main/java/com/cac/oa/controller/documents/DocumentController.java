package com.cac.oa.controller.documents;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cac.oa.convert.documents.DocumentConvert;
import com.cac.oa.entity.documents.DocumentEntity;
import com.cac.oa.service.documents.DocumentService;
import com.cac.oa.vo.documents.DocumentQuery;
import com.cac.oa.vo.documents.DocumentVO;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 文档信息 Controller
 */
@Api(tags = "OA-文档管理")
@RestController
@RequestMapping("/api/240/oa/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentConvert documentConvert;

    @GetMapping("/page")
    @ApiOperation("分页查询文档")
    public Result<PageData<DocumentVO>> page(DocumentQuery query) {
        Page<DocumentEntity> page = new Page<>(query.getPageNo(), query.getPageSize());
        LambdaQueryWrapper<DocumentEntity> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(query.getName())) {
            wrapper.like(DocumentEntity::getName, query.getName());
        }
        // 多分类范围查询（优先）：点击父分类时传入子分类ID列表
        if (query.getCategoryIds() != null && !query.getCategoryIds().isEmpty()) {
            wrapper.in(DocumentEntity::getCategoryId, query.getCategoryIds());
        } else if (query.getCategoryId() != null) {
            // 单分类精确查询（次优先）
            wrapper.eq(DocumentEntity::getCategoryId, query.getCategoryId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(DocumentEntity::getStatus, query.getStatus());
        }
        if (StringUtils.hasText(query.getUploader())) {
            wrapper.eq(DocumentEntity::getUploader, query.getUploader());
        }
        wrapper.orderByDesc(DocumentEntity::getCreateTime);

        Page<DocumentEntity> resultPage = documentService.page(page, wrapper);

        PageData<DocumentVO> pageData = new PageData<>();
        pageData.setList(documentConvert.convertList(resultPage.getRecords()));
        pageData.setTotal(resultPage.getTotal());

        return Result.ok(pageData);
    }

    @PostMapping
    @ApiOperation("保存文档")
    public Result<Boolean> save(@RequestBody DocumentEntity document) {
        // 新增文档时如果分类需要审批等逻辑也可以放在 Service 中
        // 简单处理默认给定 2 (已发布) 状态和初始版本信息
        if (!StringUtils.hasText(document.getCurrentVersion())) {
            document.setCurrentVersion("V1.0");
        }
        if (document.getStatus() == null) {
            document.setStatus(2);
        }
        return Result.ok(documentService.save(document));
    }

    @PutMapping
    @ApiOperation("更新文档")
    public Result<Boolean> update(@RequestBody DocumentEntity document) {
        return Result.ok(documentService.updateById(document));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除文档")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.ok(documentService.removeById(id));
    }

    @PostMapping("/change-version")
    @ApiOperation("更换文档版本")
    public Result<Void> changeVersion(@RequestBody Map<String, Object> params) {
        Long documentId = Long.valueOf(params.get("documentId").toString());
        String newUrl = params.get("newUrl").toString();
        String newFileName = params.get("newFileName") != null ? params.get("newFileName").toString() : null;
        Long newFileSize = params.get("newFileSize") != null ? Long.valueOf(params.get("newFileSize").toString()) : null;
        String updateLog = params.get("updateLog") != null ? params.get("updateLog").toString() : "";
        String uploader = params.get("uploader") != null ? params.get("uploader").toString() : "";

        documentService.changeDocumentVersion(documentId, newUrl, newFileName, newFileSize, updateLog, uploader);
        return Result.ok();
    }
}
