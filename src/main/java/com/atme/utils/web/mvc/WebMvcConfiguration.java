package com.atme.utils.web.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 自定义WebMvcConfiguration.
 *
 * @author S
 * @version 1.0 2020/2/12
 * @since 1.0
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    private static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/webjars/**"
    };

    private static final String[] WHITELIST = {
            "/captcha/image",
            "/user/retrieve/password/sendEmail",
            "/user/retrieve/password/auth",
            "/user/retrieve/password/do"
            ,"/tourist/**"
            ,"/login"
            ,"/login/*"
            ,"/system/getTaskLevel"
            ,"/system/getTaskTree"
            ,"/system/getTaskCategory"

    };
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(getAuthFilter()).excludePathPatterns(SWAGGER_WHITELIST).excludePathPatterns(WHITELIST).addPathPatterns("/**");
//    }
//    @Bean
//    public AuthFilterInterceptor getAuthFilter(){
//       return new AuthFilterInterceptor();
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .exposedHeaders("Authorization")
                .allowCredentials(false)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }



}
