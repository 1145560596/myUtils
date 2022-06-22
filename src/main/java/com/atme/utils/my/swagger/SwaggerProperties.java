package com.atme.utils.my.swagger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Swagger配置.
 * @author S
 * @version 1.0 2020/2/12
 * @since 1.0
 */
@ConfigurationProperties("swagger")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SwaggerProperties extends SwaggerDocketInfo{

    private Boolean enabled = false;

    private String host;

    private Map<String, SwaggerDocketInfo> dockets = new LinkedHashMap<>();
}
