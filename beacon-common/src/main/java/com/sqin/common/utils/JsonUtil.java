package com.sqin.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author Qin
 * @Date 2025/5/8 23:48
 * @Description
 **/
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String obj2JSON(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("转换JSON失败");
        }
    }

}
