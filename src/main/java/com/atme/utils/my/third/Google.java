//package com.moushi.buildx.api.web.controller.investor;
//
//import com.google.api.client.googleapis.auth.oauth2.*;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.moushi.buildx.api.admin.vo.TokenVO;
//import com.moushi.buildx.common.domain.AjaxResult;
//import com.moushi.buildx.common.domain.enums.ViewResultCodeEnum;
//import com.moushi.buildx.common.exception.ServiceException;
//import com.moushi.buildx.core.domain.GoogleAuthorization;
//import com.moushi.buildx.core.param.login.ThirdRegisterParam;
//import com.moushi.buildx.core.service.InvestorService;
//import com.moushi.buildx.core.service.ThirdLoginService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//
//@Api(tags = "谷歌登录")
//@RestController
//@RequestMapping(value = "/oauth/")
//@Slf4j
//public class GoogleOAuthController {
//
//    @Autowired
//    private GoogleAuthorization googleAuthorization;
//
//    @Autowired
//    private InvestorService investorService;
//
//    @Autowired
//    private ThirdLoginService thirdLoginService;
//
//    /**
//     * 获取授权url
//     *
//     * @return
//     * @throws GeneralSecurityException
//     * @throws IOException
//     */
//    @ApiOperation(value = "谷歌授权")
//    @PostMapping("/google/getAuthUrl")
//    public String appleLogin() throws GeneralSecurityException, IOException {
//
//        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//        // 创建验证流程对象
//        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow
//                .Builder(httpTransport, jsonFactory, googleAuthorization.getGoogleClientSecrets(), googleAuthorization.getScopes())
//                // AccessType为离线offline，才能获得Refresh Token
//                .setAccessType("offline").build();
//        if (googleAuthorizationCodeFlow != null) {
//            // 返回跳转登录请求
//            return googleAuthorizationCodeFlow.newAuthorizationUrl().setRedirectUri(googleAuthorization.getRedirectUrl()).build();
//        }
//        return null;
//    }
//
//
//    /**
//     * 解析jwt获取token
//     * @param authorizationCode
//     * @return
//     */
//    @ApiOperation("获取token")
//    @GetMapping("/google/getToken")
//    public AjaxResult<TokenVO> authorizing(String authorizationCode) {
//        //获取校验返回的token
//        GoogleIdToken idToken = null;
//        try {
//            idToken = thirdLoginService.getGoogleIdToken(authorizationCode);
//        } catch (Exception e) {
//            log.error(String.format("非法的authorizationCode：【%s】",authorizationCode));
//            throw new ServiceException(ViewResultCodeEnum.GOOGLE_AUTHORIZATION_CODE_ERROR);
//        }
//
//        //获取google登录人参数
//        ThirdRegisterParam param = thirdLoginService.getGoogleBasicParam(idToken);
//        //注册或更新投资者
//        if (!investorService.registerOrUpdateInvestor(param)) {
//            throw new ServiceException(ViewResultCodeEnum.GOOGLE_EMAIL_HAS_REGISTERED_ERROR);
//        }
//        //创建token
//        String myToken = investorService.createToken(param.getThirdId());
//
//        return AjaxResult.success(new TokenVO(myToken));
//    }
//
//
//    public GoogleIdToken getGoogleIdToken(String authorizationCode)  throws GeneralSecurityException, IOException {
//        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
//        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
//                .setAudience(Collections.singletonList(CLIENT_ID))
//                .build();
//        GoogleIdToken idToken = verifier.verify(authorizationCode);
//
//        return idToken;
//    }
//
//    public ThirdRegisterParam getGoogleBasicParam(GoogleIdToken idToken) {
//        GoogleIdToken.Payload payload = idToken.getPayload();
//
//        ThirdRegisterParam param = new ThirdRegisterParam();
//        param.setThirdId(payload.getSubject());
//        param.setEmail(payload.getEmail());
//        param.setAvatar((String) payload.get("picture"));
//        param.setFirstName((String) payload.get("given_name"));
//        param.setLastName((String) payload.get("family_name"));
//        param.setRegisterType(4);
//        return param;
//    }
//}
