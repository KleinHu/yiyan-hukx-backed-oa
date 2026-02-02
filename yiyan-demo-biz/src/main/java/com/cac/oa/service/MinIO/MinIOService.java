package com.cac.demo.service.MinIO;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * MinIO服务接口
 * 
 * @author system
 * @date 2026-01-15
 */
public interface MinIOService {

    /**
     * 上传文件（使用默认路径：/moduleName/年/月/日）
     * 
     * @param file 文件
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file);

    /**
     * 上传文件（可指定路径）
     * 
     * @param file 文件
     * @param path 文件路径（如果为空，则使用默认路径：/moduleName/年/月/日）
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file, String path);

    /**
     * 上传文件到指定完整路径
     * 
     * @param file 文件
     * @param objectName 对象名称（完整文件路径）
     * @return 文件访问URL
     */
    String uploadFileWithFullPath(MultipartFile file, String objectName);

    /**
     * 上传文件流
     * 
     * @param inputStream 文件流
     * @param objectName 对象名称（文件路径）
     * @param contentType 文件类型
     * @return 文件访问URL
     */
    String uploadFile(InputStream inputStream, String objectName, String contentType);

    /**
     * 删除文件
     * 
     * @param objectName 对象名称（文件路径）
     * @return 是否删除成功
     */
    Boolean deleteFile(String objectName);

    /**
     * 批量删除文件
     * 
     * @param objectNames 对象名称列表
     * @return 是否全部删除成功
     */
    Boolean deleteFiles(List<String> objectNames);

    /**
     * 检查文件是否存在
     * 
     * @param objectName 对象名称（文件路径）
     * @return 是否存在
     */
    Boolean fileExists(String objectName);

    /**
     * 获取文件访问URL（预签名URL，有效期7天）
     * 
     * @param objectName 对象名称（文件路径）
     * @return 文件访问URL
     */
    String getFileUrl(String objectName);

    /**
     * 获取文件访问URL（自定义有效期）
     * 
     * @param objectName 对象名称（文件路径）
     * @param expiry 有效期（秒）
     * @return 文件访问URL
     */
    String getFileUrl(String objectName, Integer expiry);

    /**
     * 下载文件
     * 
     * @param objectName 对象名称（文件路径）
     * @return 文件流
     */
    InputStream downloadFile(String objectName);

    /**
     * 复制文件
     * 
     * @param sourceObjectName 源对象名称
     * @param targetObjectName 目标对象名称
     * @return 是否复制成功
     */
    Boolean copyFile(String sourceObjectName, String targetObjectName);

    /**
     * 获取文件信息
     * 
     * @param objectName 对象名称（文件路径）
     * @return 文件大小（字节）
     */
    Long getFileSize(String objectName);
}
