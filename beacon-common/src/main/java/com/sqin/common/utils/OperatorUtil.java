package com.sqin.common.utils;

import com.sqin.common.enums.MobileOperatorEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Qin
 * @Date 2025/5/7 16:00
 * @Description
 **/
public class OperatorUtil {


    private static Map<String, Integer> operators = new HashMap<>();


    static {
        MobileOperatorEnum[] operatorEnums = MobileOperatorEnum.values();
        for (MobileOperatorEnum operatorEnum : operatorEnums) {
            operators.put(operatorEnum.getOperatorName(), operatorEnum.getOperatorId());
        }
    }

    public static Integer getOperatorIdByOperatorName(String operatorName) {
        return operators.get(operatorName);
    }

}
