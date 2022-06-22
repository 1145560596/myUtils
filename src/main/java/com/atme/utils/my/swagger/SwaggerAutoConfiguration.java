package com.atme.utils.my.swagger;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Swagger自动配置.
 * @author S
 * @version 1.0 2020/2/12
 * @since 1.0
 */
@ConditionalOnProperty(name = "swagger.enabled", havingValue = "true", matchIfMissing = false)
@Import({ Swagger2DocumentationConfiguration.class, BeanValidatorPluginsConfiguration.class })
@Configuration
public class SwaggerAutoConfiguration implements BeanFactoryAware {

    private ConfigurableBeanFactory configurableBeanFactory;

    @Bean
    @ConditionalOnMissingBean
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public List<Docket> docketApis(SwaggerProperties swaggerProperties) {
        List<Docket> docketList = Lists.newArrayList();
        if (MapUtils.isEmpty(swaggerProperties.getDockets())) {
            Docket docket = buildDocket(swaggerProperties, swaggerProperties);
            configurableBeanFactory.registerSingleton("defaultDocket", docket);
            docketList.add(docket);
            return docketList;
        }

        for (Map.Entry<String, SwaggerDocketInfo> entry : swaggerProperties.getDockets().entrySet()) {
            Docket docket = buildDocket(entry.getValue(), swaggerProperties);
            docket.groupName(entry.getKey());
            configurableBeanFactory.registerSingleton(StringUtils.removeEndIgnoreCase(entry.getKey(), "Docket") + "Docket", docket);
            docketList.add(docket);
        }

        return docketList;
    }

    private Docket buildDocket(SwaggerDocketInfo swaggerDocketInfo, SwaggerProperties swaggerProperties) {
        return new Docket(DocumentationType.SWAGGER_2).host(swaggerProperties.getHost())
                .globalOperationParameters(
                        buildParameters(swaggerDocketInfo.getGlobalOperationParameters(), swaggerProperties.getGlobalOperationParameters()))
                .apiInfo(buildApiInfo(swaggerDocketInfo, swaggerProperties)).select()
                .apis(RequestHandlerSelectors.basePackage(swaggerDocketInfo.getBasePackage())).paths(buildPaths(swaggerDocketInfo, swaggerProperties))
                .build();
    }

    private ApiInfo buildApiInfo(SwaggerApiInfo apiInfo, SwaggerApiInfo defaultApiInfo) {
        return new ApiInfoBuilder().version(ObjectUtils.defaultIfNull(apiInfo.getVersion(), defaultApiInfo.getVersion()))
                .title(ObjectUtils.defaultIfNull(apiInfo.getTitle(), defaultApiInfo.getTitle()))
                .description(ObjectUtils.defaultIfNull(apiInfo.getDescription(), defaultApiInfo.getDescription()))
                .termsOfServiceUrl(ObjectUtils.defaultIfNull(apiInfo.getTermsOfServiceUrl(), defaultApiInfo.getTermsOfServiceUrl()))
                .license(ObjectUtils.defaultIfNull(apiInfo.getLicense(), defaultApiInfo.getLicense()))
                .licenseUrl(ObjectUtils.defaultIfNull(apiInfo.getLicenseUrl(), defaultApiInfo.getLicenseUrl()))
                .contact(new Contact(ObjectUtils.defaultIfNull(apiInfo.getContact().getName(), defaultApiInfo.getContact().getName()),
                        ObjectUtils.defaultIfNull(apiInfo.getContact().getUrl(), defaultApiInfo.getContact().getUrl()),
                        ObjectUtils.defaultIfNull(apiInfo.getContact().getEmail(), defaultApiInfo.getContact().getEmail())))
                .build();
    }

    private List<Parameter> buildParameters(List<SwaggerDocketInfo.SwaggerParameter> parameters, List<SwaggerDocketInfo.SwaggerParameter> defaultParameters) {
        if (CollectionUtils.isEmpty(parameters)) {
            return buildParameter(defaultParameters);
        }

        if (CollectionUtils.isEmpty(defaultParameters)) {
            return buildParameter(parameters);
        }

        Set<String> parameterNames = parameters.stream().map(SwaggerDocketInfo.SwaggerParameter::getName).collect(Collectors.toSet());
        List<SwaggerDocketInfo.SwaggerParameter> mergedParameters = Lists.newArrayList();
        for (SwaggerDocketInfo.SwaggerParameter parameter : defaultParameters) {
            if (!parameterNames.contains(parameter.getName())) {
                mergedParameters.add(parameter);
            }
        }

        mergedParameters.addAll(parameters);
        return buildParameter(mergedParameters);
    }

    private List<Parameter> buildParameter(List<SwaggerDocketInfo.SwaggerParameter> swaggerParameters) {
        List<Parameter> parameters = Lists.newArrayList();
        if (CollectionUtils.isEmpty(swaggerParameters)) {
            return parameters;
        }

        for (SwaggerDocketInfo.SwaggerParameter swaggerParameter : swaggerParameters) {
            parameters.add(new ParameterBuilder().name(swaggerParameter.getName()).description(swaggerParameter.getDescription())
                    .required(swaggerParameter.getRequired()).modelRef(new ModelRef(swaggerParameter.getModelRef()))
                    .parameterType(swaggerParameter.getParamType()).build());
        }

        return parameters;
    }

    private Predicate<String> buildPaths(SwaggerDocketInfo swaggerDocketInfo, SwaggerProperties swaggerProperties) {
        Set<String> includePaths = swaggerDocketInfo.getIncludePaths();
        includePaths.addAll(swaggerProperties.getIncludePaths());
        if (CollectionUtils.isEmpty(includePaths)) {
            includePaths.add("/**");
        }

        Set<String> excludePaths = swaggerDocketInfo.getExcludePaths();
        excludePaths.addAll(swaggerProperties.getExcludePaths());

        List<Predicate<String>> includePathPredicates = includePaths.stream().map(e -> PathSelectors.ant(e)).collect(Collectors.toList());
        List<Predicate<String>> excludePathPredicates = excludePaths.stream().map(e -> PathSelectors.ant(e)).collect(Collectors.toList());
        return Predicates.and(Predicates.or(includePathPredicates), Predicates.not(Predicates.or(excludePathPredicates)));
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
    }
}
