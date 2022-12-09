package com.atme.utils.newirs;

import com.alibaba.fastjson.JSONArray;
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

public class Demo_shengjiekou {

    public static void main(String[] args) throws Exception{
        getProvinceApi();
    }

    public static String getProvinceApi() throws Exception {
        //请求接口地址（政务网环境根据接口文档地址改动）
        //String url="https://sql.hz.gov.cn/ESBWeb/servlets/33.1111.zj.1k4t6FP7ea6513o5.SynReq@1.0";//浙江省杭州健康码核验接口
        String url="https://sql.hz.gov.cn/ESBWeb/servlets/33.1111.zj.8fGO7c2lb5S13Q0a.SynReq@1.0";//浙江省核酸检测数据查询
        //获取appkey 和请求秘钥 每次请求健康码接口都要重获一次requestSecret 浙江省接口秘钥申请
        String result = post("https://sql.hz.gov.cn/ESBWeb/servlets/33.1111.zj.appkeyAndrequestToken.SynReq@1.0",null);
        JSONObject json = JSONObject.parseObject(result);
        JSONArray data = json.getJSONArray("data");
        String requestToken = data.getJSONObject(0).getString("requestSecret");
        String appKey = data.getJSONObject(0).getString("app_key");

        System.out.println("浙江省接口秘钥申请，返回token--->" + requestToken);
        long requestTime = System.currentTimeMillis();
        String sign = MD5Utils.encodeByMD5(appKey+requestToken+requestTime);
        Part[] parts1 = {
                new StringPart("appKey",appKey),
                new StringPart("sign",sign),
                new StringPart("requestTime",String.valueOf(requestTime)),
//              //new StringPart("additional","additional","UTF-8")
                new StringPart("idcardNo","身份证号码","UTF-8")
        };
        result = post(url,parts1);
        System.out.println("省接口调用结果--->" + result);
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


