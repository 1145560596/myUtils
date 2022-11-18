package com.atme.utils.irs;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;

import java.net.URLEncoder;

public class Demo_shijiekou {

    private static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKLQgp07slkmHn/NrfvsaGrzI/TsiJ2KhXtQ9eNofWmX/NyJFROVCORfILRCL5e1qS+aFcuYg/jekQxBwOpDMxEStIy+RKySPms6VOS/abEZ1UKsxK8deLrNqoiwITL9uiKsOmTp2ULZ76ntwrKVxxmPQr6wlu3F7L8DyyE1SnoHAgMBAAECgYEAm2JH5WtdsLsyjGJtU2qx1LArdspvL3tOHPyNTvUgC7CkAI1Lch7gF6O6AI7SAQW8a9OwTVhHSzKOV5ZBWNG9X12kzWinHQbxiItFdWTvlCBziS/fGbzIyO+O6HmtTCFdIBz0PBy30q9UDaHdwB3kCd+VDLabWKsaM8A0KTUxmFECQQDXvj08NLG0x/kpGnH9GzhDu7H5UH5Ich2McO4h+EGq/s+dBX1aWfkLlRZgWV1ltOpe0Zz47ZLTKu8APygqQnNFAkEAwTHw690v8VX/m5I/kgvVdeQ3/fQIYKTI6tjk/Ozf8Q9KRojD9snCMUc+z760UYbHNE9jTgu8vokBr0fcF4lG2wJAVEN0bVhzdBWK5pfyn5YLEsFzkNn0iN0xV1IgYFozY9MkScMEI87ya6iuVbFxvjC8PY6HTd6Usy+Yq7L/QAo2NQJABk10yJ0MpVji39ZjkIYmTpRFZ1mAtHZrv42X2tB3dcvD5o0rp29pkGX8nJZiF47IDOLSIIetfqHFlkxH19S4pQJAINvZg5QNNSnjWYUr/S50CJXaEoG/zVfObpcP71R9qdAGvZYkuqFedzeG6kWHgUVyHgw46fhMNRITKMr4MBifkw==";

    public static void main(String[] args) throws Exception{
        getCityApi();
    }

    public static String getCityApi() throws Exception{
        //请求接口地址（政务网环境根据接口文档地址改动）
        String url ="https://sql.hz.gov.cn/ESBWeb/servlets/33.1111.zjhz.smzx.cgjParkingInfoNew.SynReq@1.0";// 浙江杭州城管局停车场基本信息查询
        JSONObject json = new JSONObject();
        json.put("areaCode","07");
        String request = RSAUtils.encryptByPrivateKey(json.toJSONString(), privateKey);
        String sign = RSAUtils.sign(request,privateKey);

        Part[] parts = {
                new StringPart("request", request,"UTF-8"),
                new StringPart("sign", sign,"UTF-8")
        };
        String result = post(url,parts);

        System.out.println("市接口调用结果--->" + result);
        return result;
    }

    //post方式
    private static String post(String url, Part[] parts){
        /**导入SSL证书 [解决PKIX path building failed错误问题]**/
        initCert(url);
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        if(parts != null) post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
        String response="";
        try {
            //按需加载请求头参数
            post.setRequestHeader(new Header("systemDepartmentCode", URLEncoder.encode("请求部门","utf-8")));
            post.setRequestHeader(new Header("areaCode", URLEncoder.encode("所在行政区域","utf-8")));
            client.executeMethod(post);
            response = IOUtils.toString(post.getResponseBodyAsStream(),"UTF-8");
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            post.releaseConnection();
        }
        return response;
    }

    private static void initCert(final String url){
        /**
         * 导入SSL证书 [解决PKIX path building failed错误问题]
         *    --使用[javax.net.ssl.trustStore]不能解决多域名证书问题
         *    --建议将生成的证书文件 copy 到$JAVA_HOME/jre/lib/security目录下
         */
        String[] c = url.replaceFirst("(http|https)://","").split("/")[0].split(":");
        String host = c[0];
        Integer port = (c.length == 1) ? 443 : Integer.parseInt(c[1]);
        if(System.getProperty(host + "ssl_cert_path") != null) return;
        try {
            String sslCertPath = InstallCert.createCert(host,port);
            System.out.println("证书文件生成路径--->" + sslCertPath);
            System.setProperty(host + "ssl_cert_path", sslCertPath);
            System.setProperty("javax.net.ssl.trustStore", sslCertPath);
        }catch (Exception e){
            e.printStackTrace();
        }
        /****证书导入成功****/
    }
}


