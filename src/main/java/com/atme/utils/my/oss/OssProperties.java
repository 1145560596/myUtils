package com.atme.utils.my.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云oss配置.
 *
 * @author S
 * @version 1.0 2020/6/5
 * @since 1.0
 */
@Component
@ConfigurationProperties(prefix = "oss")
@Data
public class OssProperties {

    private String bucketName;

    private String endPoint;

    private String domainName;

    private String filePath;

    private String accessKeyId;

    private String accessKeySecret;
}
