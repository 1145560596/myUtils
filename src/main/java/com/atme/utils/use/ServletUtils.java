package com.atme.utils.use;

import com.atme.utils.my.result.Result;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Servlet工具类.
 *
 * @author S
 * @version 1.0 2020/2/12
 * @since 1.0
 */
@Slf4j
public abstract class ServletUtils {

    private static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";

    public static Map<String, String> getParameterMap(ServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        CollectionUtils.toIterator(request.getParameterNames()).forEachRemaining(e -> {
            map.put(e, request.getParameter(e));
        });

        return map;
    }

    public static Map<String, String> getBodyMap(ServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(request.getInputStream(), "UTF-8");
            BufferedReader streamReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            map = JsonUtils.getObjectFromJson(sb.toString(), new TypeReference<Map<String, String>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public static void write(ServletResponse response, Result<?> result) throws IOException {
        response.setContentType(CONTENT_TYPE_JSON);
        response.getWriter().write(JsonUtils.getJsonFromObject(result));
    }

    public static String getRemoteIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

}
