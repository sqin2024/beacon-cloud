package com.sqin.strategy.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


/**
 * @Author Qin
 * @Date 2025/5/7 16:04
 * @Description
 **/
@Component
public class MobileOperatorUtil {
    private static final String CODE = "code";
    private static final String DATA = "data";
    private static final String PROVINCE = "province";
    private static final String CITY = "city";
    private static final String SP = "sp";
    private static final String SPACE = " ";
    private static final String SEPARATE = ",";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final String url1 = "https://cx.shouji.360.cn/phonearea.php?number=";

    public String getMobileInfoBy360(String mobile) {
        String mobileInfoJSON = restTemplate.getForObject(url1 + mobile, String.class);
        Map map = null;
        try {
            map = objectMapper.readValue(mobileInfoJSON, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Integer code = (Integer) map.get(CODE);
        if (code != 0) {
            return null;
        }
        Map<String, String> areaAndOperator = (Map<String, String>) map.get(DATA);
        String province = areaAndOperator.get(PROVINCE);
        String city = areaAndOperator.get(CITY);
        String sp = areaAndOperator.get(SP);

        return province + SPACE + city + SEPARATE + sp;
    }

}
