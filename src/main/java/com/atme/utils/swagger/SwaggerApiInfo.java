package com.atme.utils.swagger;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Swagger接口信息.
 *
 * @author S
 * @version 1.0 2020/2/12
 * @since 1.0
 */
@Data
public class SwaggerApiInfo {

    private String version;

    private String title;

    private String description;

    private String termsOfServiceUrl;

    private String license;

    private String licenseUrl;

    private SwaggerContact contact = new SwaggerContact();

    @Data
    @NoArgsConstructor
    public static class SwaggerContact {

        private String name;

        private String url;

        private String email;
    }
}
