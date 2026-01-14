package com.cac.demo.service.MinIO.impl;

import com.cac.demo.config.MinIOConfig;
import com.cac.demo.service.MinIO.MinIOService;
import com.cac.yiyan.common.exception.service.ServiceException;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * MinIO服务实现类
 *
 * @author system
 * @date 2026-01-15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MinIOServiceImpl implements MinIOService {

    private final MinioClient minioClient;
    private final MinIOConfig minIOConfig;

    /**
     * 上传文件（使用默认路径：/moduleName/年/月/日）
     */
    @Override
    public String uploadFile(MultipartFile file) {
        return uploadFile(file, null);
    }

    /**
     * 上传文件（可指定路径）
     */
    @Override
    public String uploadFile(MultipartFile file, String path) {
        try {
            // 生成文件名
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString().replace("-", "") + extension;

            // 构造文件路径
            String objectName;
            if (StringUtils.hasText(path)) {
                // 如果指定了路径，使用指定路径
                // 确保路径以 / 开头，不以 / 结尾
                String normalizedPath = path.startsWith("/") ? path : "/" + path;
                normalizedPath = normalizedPath.endsWith("/") ? normalizedPath.substring(0, normalizedPath.length() - 1) : normalizedPath;
                objectName = normalizedPath + "/" + fileName;
            } else {
                // 如果路径为空，使用默认路径：/moduleName/年/月/日（月份和日期不带前导零）
                String moduleName = StringUtils.hasText(minIOConfig.getModuleName()) ? minIOConfig.getModuleName() : "default";
                LocalDate now = LocalDate.now();
                String datePath = now.getYear() + "/" + now.getMonthValue() + "/" + now.getDayOfMonth();
                objectName = "/" + moduleName + "/" + datePath + "/" + fileName;
            }

            // 确保路径没有双斜杠
            objectName = objectName.replaceAll("/+", "/");

            return uploadFileWithFullPath(file, objectName);
        } catch (Exception e) {
            log.error("上传文件失败: {}", e.getMessage(), e);
            throw new ServiceException("上传文件失败: " + e.getMessage());
        }
    }

    /**
     * 上传文件到指定完整路径
     */
    @Override
    public String uploadFileWithFullPath(MultipartFile file, String objectName) {
        try {
            // 确保桶存在
            ensureBucketExists();

            // 上传文件
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minIOConfig.getBucketName())
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            // 返回文件访问URL（直接拼接）
            return buildFileUrl(objectName);
        } catch (Exception e) {
            log.error("上传文件失败: {}", e.getMessage(), e);
            throw new ServiceException("上传文件失败: " + e.getMessage());
        }
    }

    /**
     * 上传文件流
     */
    @Override
    public String uploadFile(InputStream inputStream, String objectName, String contentType) {
        try {
            // 确保桶存在
            ensureBucketExists();

            // 上传文件流
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minIOConfig.getBucketName())
                            .object(objectName)
                            .stream(inputStream, -1, 10485760) // 默认10MB
                            .contentType(contentType)
                            .build()
            );

            // 返回文件访问URL（直接拼接）
            return buildFileUrl(objectName);
        } catch (Exception e) {
            log.error("上传文件流失败: {}", e.getMessage(), e);
            throw new ServiceException("上传文件流失败: " + e.getMessage());
        }
    }

    /**
     * 删除文件
     */
    @Override
    public Boolean deleteFile(String objectName) {
        try {
            if (!StringUtils.hasText(objectName)) {
                return false;
            }

            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(minIOConfig.getBucketName())
                            .object(objectName)
                            .build()
            );

            return true;
        } catch (Exception e) {
            log.error("删除文件失败: {}", e.getMessage(), e);
            throw new ServiceException("删除文件失败: " + e.getMessage());
        }
    }

    /**
     * 批量删除文件
     */
    @Override
    public Boolean deleteFiles(List<String> objectNames) {
        try {
            if (objectNames == null || objectNames.isEmpty()) {
                return true;
            }

            for (String objectName : objectNames) {
                if (StringUtils.hasText(objectName)) {
                    deleteFile(objectName);
                }
            }

            return true;
        } catch (Exception e) {
            log.error("批量删除文件失败: {}", e.getMessage(), e);
            throw new ServiceException("批量删除文件失败: " + e.getMessage());
        }
    }

    /**
     * 检查文件是否存在
     */
    @Override
    public Boolean fileExists(String objectName) {
        try {
            if (!StringUtils.hasText(objectName)) {
                return false;
            }

            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(minIOConfig.getBucketName())
                            .object(objectName)
                            .build()
            );

            return true;
        } catch (ErrorResponseException e) {
            if (e.errorResponse().code().equals("NoSuchKey")) {
                return false;
            }
            log.error("检查文件是否存在失败: {}", e.getMessage(), e);
            throw new ServiceException("检查文件是否存在失败: " + e.getMessage());
        } catch (Exception e) {
            log.error("检查文件是否存在失败: {}", e.getMessage(), e);
            throw new ServiceException("检查文件是否存在失败: " + e.getMessage());
        }
    }

    /**
     * 获取文件访问URL（默认7天有效期）
     */
    @Override
    public String getFileUrl(String objectName) {
        return getFileUrl(objectName, 7 * 24 * 60 * 60); // 7天
    }

    /**
     * 获取文件访问URL（自定义有效期）
     */
    @Override
    public String getFileUrl(String objectName, Integer expiry) {
        try {
            if (!StringUtils.hasText(objectName)) {
                return null;
            }

            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(minIOConfig.getBucketName())
                            .object(objectName)
                            .expiry(expiry)
                            .build()
            );

            return url;
        } catch (Exception e) {
            log.error("获取文件访问URL失败: {}", e.getMessage(), e);
            throw new ServiceException("获取文件访问URL失败: " + e.getMessage());
        }
    }

    /**
     * 下载文件
     */
    @Override
    public InputStream downloadFile(String objectName) {
        try {
            if (!StringUtils.hasText(objectName)) {
                return null;
            }

            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minIOConfig.getBucketName())
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            log.error("下载文件失败: {}", e.getMessage(), e);
            throw new ServiceException("下载文件失败: " + e.getMessage());
        }
    }

    /**
     * 复制文件
     */
    @Override
    public Boolean copyFile(String sourceObjectName, String targetObjectName) {
        try {
            if (!StringUtils.hasText(sourceObjectName) || !StringUtils.hasText(targetObjectName)) {
                return false;
            }

            // 确保目标桶存在
            ensureBucketExists();

            // 复制对象
            minioClient.copyObject(
                    CopyObjectArgs.builder()
                            .bucket(minIOConfig.getBucketName())
                            .object(targetObjectName)
                            .source(
                                    CopySource.builder()
                                            .bucket(minIOConfig.getBucketName())
                                            .object(sourceObjectName)
                                            .build()
                            )
                            .build()
            );

            return true;
        } catch (Exception e) {
            log.error("复制文件失败: {}", e.getMessage(), e);
            throw new ServiceException("复制文件失败: " + e.getMessage());
        }
    }

    /**
     * 获取文件大小
     */
    @Override
    public Long getFileSize(String objectName) {
        try {
            if (!StringUtils.hasText(objectName)) {
                return 0L;
            }

            StatObjectResponse statObject = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(minIOConfig.getBucketName())
                            .object(objectName)
                            .build()
            );

            return statObject.size();
        } catch (Exception e) {
            log.error("获取文件大小失败: {}", e.getMessage(), e);
            throw new ServiceException("获取文件大小失败: " + e.getMessage());
        }
    }

    /**
     * 构建文件访问URL（直接拼接）
     *
     * @param objectName 对象名称（文件路径）
     * @return 文件访问URL
     */
    private String buildFileUrl(String objectName) {
        if (!StringUtils.hasText(objectName)) {
            return null;
        }

        // 规范化路径：去除开头的斜杠（如果有），确保路径格式正确
        String normalizedPath = objectName.startsWith("/") ? objectName.substring(1) : objectName;

        // 拼接URL：endpoint/bucketName/objectName
        String endpoint = minIOConfig.getEndpoint();
        String bucketName = minIOConfig.getBucketName();

        // 确保endpoint不以斜杠结尾
        if (endpoint.endsWith("/")) {
            endpoint = endpoint.substring(0, endpoint.length() - 1);
        }

        return endpoint + "/" + bucketName + "/" + normalizedPath;
    }

    /**
     * 确保桶存在，如果不存在则创建
     */
    private void ensureBucketExists() {
        try {
            boolean found = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(minIOConfig.getBucketName())
                            .build()
            );

            if (!found) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(minIOConfig.getBucketName())
                                .build()
                );
                log.info("创建MinIO桶: {}", minIOConfig.getBucketName());
            }
        } catch (Exception e) {
            log.error("检查或创建桶失败: {}", e.getMessage(), e);
            throw new ServiceException("检查或创建桶失败: " + e.getMessage());
        }
    }
}
