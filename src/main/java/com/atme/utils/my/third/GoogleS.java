//package com.moushi.buildx.core.domain;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Collections;
//import java.util.List;
//
///**
// * google授权
// */
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Data
//public class GoogleAuthorization {
//
//    // 应用名
//    private String applicationName;
//    // 重定向路径
//    private String redirectUrl;
//    // 应用凭证
//    private GoogleClientSecrets googleClientSecrets;
//
//    // 授权域
//    private final static List<String> scopes = Collections.singletonList(
//            "https://www.googleapis.com/auth/userinfo.email"
//    );
//
//    public List<String> getScopes(){
//        return scopes;
//    }
//}