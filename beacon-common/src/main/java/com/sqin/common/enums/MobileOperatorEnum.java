package com.sqin.common.enums;

import lombok.Getter;

/**
 * @Author Qin
 * @Date 2025/5/7 15:57
 * @Description
 **/
@Getter
public enum MobileOperatorEnum {


    CHINA_MOBILE(1, "移动"),
    CHINA_UNICOM(2, "联通"),
    CHINA_TELECOM(3, "电信");
    private Integer operatorId;

    private String operatorName;

    MobileOperatorEnum(Integer operatorId, String operatorName) {
        this.operatorId = operatorId;
        this.operatorName = operatorName;
    }

}
