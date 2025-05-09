package com.sqin.common.utils;

import com.sqin.common.enums.CMPP2ResultEnums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Qin
 * @Date 2025/5/7 16:00
 * @Description
 **/
public class CMPP2ResultUtil {

    private static Map<Integer, String> operators = new HashMap<>();

    static {
        CMPP2ResultEnums[] operatorEnums = CMPP2ResultEnums.values();
        for (CMPP2ResultEnums operatorEnum : operatorEnums) {
            operators.put(operatorEnum.getResult(), operatorEnum.getMsg());
        }
    }

    public static String getResultMessage(Integer operatorName) {
        return operators.get(operatorName);
    }

}
