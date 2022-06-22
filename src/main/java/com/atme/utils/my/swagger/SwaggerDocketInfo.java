package com.atme.utils.my.swagger;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;

/**
 * Swagger摘要信息.
 *
 * @author S
 * @version 1.0 2020/2/12
 * @since 1.0
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SwaggerDocketInfo extends SwaggerApiInfo {

    private List<SwaggerParameter> globalOperationParameters = Lists.newArrayList();

    private String basePackage = "";

    private Set<String> includePaths = Sets.newHashSet();

    private Set<String> excludePaths = Sets.newHashSet();

    @Data
    @NoArgsConstructor
    public static class SwaggerParameter {

        private String name;

        private String description;

        private Boolean required = false;

        private String modelRef;

        private String paramType;
    }
}
