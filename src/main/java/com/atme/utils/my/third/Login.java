//package com.moushi.buildx.api.web.controller.investor;
//
//import com.google.gson.Gson;
//import com.moushi.buildx.api.admin.vo.TokenVO;
//import com.moushi.buildx.common.domain.AjaxResult;
//import com.moushi.buildx.common.domain.enums.ViewResultCodeEnum;
//import com.moushi.buildx.common.exception.ServiceException;
//import com.moushi.buildx.core.controller.BaseController;
//import com.moushi.buildx.core.param.login.ThirdRegisterParam;
//import com.moushi.buildx.core.service.InvestorService;
//import com.moushi.buildx.core.service.ThirdLoginService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import javax.xml.ws.WebServiceException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.UUID;
//
///**
// * @author amao
// * @create 2022-07-13-11:29
// */
//@Api(tags = "微信接口")
//@Controller
//@Slf4j
//@RequestMapping("/oauth/wxi")
//public class Login extends{
//
//    @Autowired
//    private InvestorService investorService;
//
//    @Autowired
//    private ThirdLoginService thirdLoginService;
//
//    @ApiOperation("生成微信扫描二维码")
//    @GetMapping("/login")
//    public String genQrConnect(HttpServletRequest request) {
//        String url = thirdLoginService.createWxLoginUrl(request);
//        return "redirect:" + url;
//    }
//
//    //获取扫描人信息，添加数据
//    @GetMapping("/callback")
//    @ApiOperation("微信回调")
//    public AjaxResult<TokenVO> callback(String code) {
//        try {
//            //获取微信登录人参数
//            ThirdRegisterParam param = thirdLoginService.getWxBasicParam(code);
//            //注册或更新投资者
//            investorService.registerOrUpdateInvestor(param);
//            //创建token
//            String token = investorService.createToken(param.getThirdId());
//            return AjaxResult.success(new TokenVO(token));
//        } catch (Exception e) {
//            throw new ServiceException(ViewResultCodeEnum.WXI_LOGIN_ERROR);
//        }
//    }
//
//
//    public String createWxLoginUrl(HttpServletRequest request) {
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        HttpSession session = request.getSession();
//        session.setAttribute("state", uuid);
//
//        //微信开放平台授权baseUrl
//        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
//                "?appid=%s" +
//                "&redirect_uri=%s" +
//                "&response_type=code" +
//                "&scope=snsapi_login" +
//                "&state=%s" +
//                "#wechat_redirect";
//        //获取业务服务器重定向地址
//        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
//        try {
//            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            throw new WebServiceException();
//        }
//
//        String url = String.format(
//                baseUrl,
//                ConstantWxUtils.WX_OPEN_APP_ID,
//                redirectUrl,
//                uuid
//        );
//        return url;
//    }
//
//    public ThirdRegisterParam getWxBasicParam(String code) {
//        //向认证服务器发送请求换取access_token
//        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
//                "?appid=%s" +
//                "&secret=%s" +
//                "&code=%s" +
//                "&grant_type=authorization_code";
//        String accessTokenUrl = String.format(
//                baseAccessTokenUrl,
//                ConstantWxUtils.WX_OPEN_APP_ID,
//                ConstantWxUtils.WX_OPEN_APP_SECRET,
//                code
//        );
//
//        String accessTokenInfo = HttpUtils.get(accessTokenUrl,null);
//        Gson gson = new Gson();
//        HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
//        String access_token = (String) mapAccessToken.get("access_token");
//        String openid = (String) mapAccessToken.get("openid");
//
//        //拿着得到的accesss_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
//        //访问微信的资源服务器，获取用户信息
//        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
//                "?access_token=%s" +
//                "&openid=%s";
//        String userInfoUrl = String.format(
//                baseUserInfoUrl,
//                access_token,
//                openid
//        );
//        //发送请求
//        String userInfo = HttpUtils.get(userInfoUrl,null);
//        HashMap map = gson.fromJson(userInfo, HashMap.class);
//        String headimgurl = (String) map.get("headimgurl");
//        String nickname = (String) map.get("nickname");
//
//        ThirdRegisterParam param = new ThirdRegisterParam();
//        param.setThirdId(openid);
//        param.setAvatar(headimgurl);
//        param.setFirstName(nickname);
//        param.setRegisterType(2);
//        return param;
//    }
//
//}
