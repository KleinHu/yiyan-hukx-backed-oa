package com.cac.demo.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MinIO配置类
 * 
 * @author system
 * @date 2026-01-15
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinIOConfig {

    /**
     * MinIO服务地址
     */
    private String endpoint;

    /**
     * 访问密钥
     */
    private String accessKey;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 默认桶名
     */
    private String bucketName;

    /**
     * 模块名称（用于文件路径前缀）
     */
    private String moduleName;

    /**
     * 创建MinioClient Bean
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
