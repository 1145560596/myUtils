package com.atme.utils.my.filter.inputstream;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class HttpContextUtils {

    /**
     * 获取query参数
     * @param request
     * @return
     */
    public static Map<String, String> getParameterMapAll(HttpServletRequest request) {
        Enumeration<String> parameters = request.getParameterNames();

        Map<String, String> params = new HashMap<>();
        while (parameters.hasMoreElements()) {
            String parameter = parameters.nextElement();
            String value = request.getParameter(parameter);
            params.put(parameter, value);
        }

        return params;
    }

    /**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        JSONObject jsonObject = (JSONObject) JSONArray.parse(sb.toString());
        jsonObject.values().forEach(e->{
            if(null == e|| e.equals("")){
                return;
            }
            if(e instanceof String){
                if(sqlValidate(e.toString())){
                    throw new WebServiceException("参数中含有非法字符！请检查参数！");
                }
            }
        });

        return sb.toString();
    }

    public static boolean sqlValidate(String str){
        //统一转为小写
        String s = str.toLowerCase();
        String re =
                "select|and|or|delete|insert|truncate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute|table|char|declare|sitename|xp_cmdshell|like|from|grant|use|group_concat|column_name|information_schema.columns|table_schema|union|where|order|by|if|\\*|\\;|\\--|\\+|\\%|\\#";

        String inj_stra[] = re.split("\\|");
        for (int i = 0; i < inj_stra.length; i++) {
            if (s.indexOf(inj_stra[i].replace("\\","")) >= 0) {
                return true;
            }
        }
        return  false;
    }

}

