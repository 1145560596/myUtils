//package com.atme.utils.my.third;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.moushi.buildx.core.domain.GoogleAuthorization;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * google登录授权配置
// */
//@Configuration
//@Slf4j
//public class GoogleAuthorizationConfig {
//
//    @Value("${google.clientId}")
//    private String clientId;
//
//    @Value("${google.clientSecret}")
//    private String clientSecret;
//
//    @Value("${google.applicationName}")
//    private String applicationName;
//
//    @Value("${google.redirectUrl}")
//    private String redirectUrl;
//
//    @Bean(name = "GoogleAuthorization")
//    public GoogleAuthorization googleFeed() {
//        GoogleClientSecrets clientSecrets = null;
//        try {
//            GoogleClientSecrets.Details details = new GoogleClientSecrets.Details();
//            details.setClientId(clientId);
//            details.setClientSecret(clientSecret);
//            clientSecrets = new GoogleClientSecrets();
//            clientSecrets.setInstalled(details);
//        } catch (Exception e) {
//            log.error("authorization configuration error:{}", e.getMessage());
//        }
//        // 构建bean
//        return GoogleAuthorization.builder()
//                .googleClientSecrets(clientSecrets)
//                .applicationName(applicationName)
//                .redirectUrl(redirectUrl)
//                .build();
//    }
//}
