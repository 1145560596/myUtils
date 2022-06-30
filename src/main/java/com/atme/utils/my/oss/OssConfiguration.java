package com.atme.utils.my.oss;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 阿里云oss配置.
 *
 * @author S
 * @version 1.0 2020/6/5
 * @since 1.0
 */
@Component
@EnableConfigurationProperties({OssProperties.class})
public class OssConfiguration {

    @Autowired
    private OssProperties ossProperties;

    @Bean
    OSSClient ossClient() {
        return new OSSClient(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
    }

}
