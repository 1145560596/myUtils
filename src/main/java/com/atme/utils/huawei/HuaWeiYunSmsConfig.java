package com.atme.utils.huawei;

import lombok.Data;

/**
 * @author amao
 * @create 2023-04-24-11:49
 */
@Data
public class HuaWeiYunSmsConfig {

    //短信发送apiURI
    private String url;

    //APP_Key
    private String appKey;

    //APP_Secret
    private String appSecret ;

    //国内短信签名通道号或国际/港澳台短信通道号
    private String sender ;

    //模板ID
    private String templateId ;

    //签名名称
    private String signature;

    //返回信息回调URI
    //选填,短信状态报告接收地址,推荐使用域名,为空或者不填表示不接收状态报告
    private String statusCallBack;

    private String templateParam ;

    private String toPhoneNumber;

}

