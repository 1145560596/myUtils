package com.atme.utils.use;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

/**
 * Json工具类.
 *
 * @author S
 * @version 1.0 2020/2/12
 * @since 1.0
 */
public class JsonUtils {

    private static ObjectMapper JSON = new ObjectMapper();

    static {
        JSON.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JSON.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSON.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static String getJsonFromObject(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return JSON.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T getObjectFromJson(String json, Class<T> valueType) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return JSON.readValue(json, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T getObjectFromJson(String json, TypeReference<T> valueTypeRef) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return JSON.readValue(json, valueTypeRef);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
