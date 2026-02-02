package com.cac.oa.controller.File;

import com.cac.oa.service.MinIO.MinIOService;
import com.cac.yiyan.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传控制器
 *
 * @author system
 * @date 2026-01-15
 */
@Slf4j
@Api(tags = "文件上传管理")
@RestController
@RequestMapping("/api/hr/file")
@RequiredArgsConstructor
public class FileUploadController {

    private final MinIOService minIOService;

    /**
     * 单文件上传
     */
    @ApiOperation("单文件上传")
    @PostMapping("/upload")
    public Result<Map<String, Object>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) String path) {
        try {
            if (file == null || file.isEmpty()) {
                return Result.error("文件不能为空");
            }

            // 上传文件
            String fileUrl = minIOService.uploadFile(file, path);

            // 构建返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("fileName", file.getOriginalFilename());
            result.put("fileSize", file.getSize());
            result.put("contentType", file.getContentType());
            result.put("url", fileUrl);

            return Result.ok(result);
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            return Result.error("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 多文件上传
     */
    @ApiOperation("多文件上传")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "files", value = "文件列表", required = true, paramType = "form", dataType = "file", allowMultiple = true),
        @ApiImplicitParam(name = "path", value = "文件路径（可选，为空则使用默认路径：/moduleName/年/月/日）", paramType = "query", dataType = "String")
    })
    @PostMapping("/upload/batch")
    public Result<List<Map<String, Object>>> uploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(required = false) String path) {
        try {
            if (files == null || files.length == 0) {
                return Result.error("文件列表不能为空");
            }

            List<Map<String, Object>> resultList = new ArrayList<>();
            List<String> errorList = new ArrayList<>();

            // 逐个上传文件
            for (MultipartFile file : files) {
                try {
                    if (file == null || file.isEmpty()) {
                        errorList.add(file != null ? file.getOriginalFilename() : "未知文件" + "：文件为空");
                        continue;
                    }

                    // 上传文件
                    String fileUrl = minIOService.uploadFile(file, path);

                    // 构建文件信息
                    Map<String, Object> fileInfo = new HashMap<>();
                    fileInfo.put("fileName", file.getOriginalFilename());
                    fileInfo.put("fileSize", file.getSize());
                    fileInfo.put("contentType", file.getContentType());
                    fileInfo.put("url", fileUrl);
                    fileInfo.put("success", true);

                    resultList.add(fileInfo);
                } catch (Exception e) {
                    log.error("文件上传失败: {} - {}", file != null ? file.getOriginalFilename() : "未知文件", e.getMessage());

                    Map<String, Object> fileInfo = new HashMap<>();
                    fileInfo.put("fileName", file != null ? file.getOriginalFilename() : "未知文件");
                    fileInfo.put("success", false);
                    fileInfo.put("error", e.getMessage());

                    resultList.add(fileInfo);
                    errorList.add((file != null ? file.getOriginalFilename() : "未知文件") + "：" + e.getMessage());
                }
            }

            // 如果有部分文件上传失败，返回警告信息
            if (!errorList.isEmpty()) {
                return Result.ok("部分文件上传失败：" + String.join("; ", errorList),resultList);
            }

            return Result.ok(resultList);
        } catch (Exception e) {
            log.error("批量文件上传失败: {}", e.getMessage(), e);
            return Result.error("批量文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 删除文件
     */
    @ApiOperation("删除文件")
    @ApiImplicitParam(name = "objectName", value = "文件路径（对象名称）", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/delete")
    public Result<Boolean> deleteFile(@RequestParam("objectName") String objectName) {
        try {
            if (objectName == null || objectName.trim().isEmpty()) {
                return Result.error("文件路径不能为空");
            }

            Boolean result = minIOService.deleteFile(objectName);
            if (result) {
                return Result.ok("文件删除成功", true);
            } else {
                return Result.error("文件删除失败");
            }
        } catch (Exception e) {
            log.error("文件删除失败: {}", e.getMessage(), e);
            return Result.error("文件删除失败: " + e.getMessage());
        }
    }

    /**
     * 检查文件是否存在
     */
    @ApiOperation("检查文件是否存在")
    @ApiImplicitParam(name = "objectName", value = "文件路径（对象名称）", required = true, paramType = "query", dataType = "String")
    @GetMapping("/exists")
    public Result<Boolean> fileExists(@RequestParam("objectName") String objectName) {
        try {
            if (objectName == null || objectName.trim().isEmpty()) {
                return Result.error("文件路径不能为空");
            }

            Boolean exists = minIOService.fileExists(objectName);
            return Result.ok(exists);
        } catch (Exception e) {
            log.error("检查文件是否存在失败: {}", e.getMessage(), e);
            return Result.error("检查文件是否存在失败: " + e.getMessage());
        }
    }

    /**
     * 获取文件访问URL
     */
    @ApiOperation("获取文件访问URL")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "objectName", value = "文件路径（对象名称）", required = true, paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "expiry", value = "有效期（秒，可选，默认7天）", paramType = "query", dataType = "int")
    })
    @GetMapping("/url")
    public Result<String> getFileUrl(
            @RequestParam("objectName") String objectName,
            @RequestParam(required = false) Integer expiry) {
        try {
            if (objectName == null || objectName.trim().isEmpty()) {
                return Result.error("文件路径不能为空");
            }

            String fileUrl;
            if (expiry != null && expiry > 0) {
                fileUrl = minIOService.getFileUrl(objectName, expiry);
            } else {
                fileUrl = minIOService.getFileUrl(objectName);
            }

            return Result.ok(fileUrl);
        } catch (Exception e) {
            log.error("获取文件访问URL失败: {}", e.getMessage(), e);
            return Result.error("获取文件访问URL失败: " + e.getMessage());
        }
    }
}
