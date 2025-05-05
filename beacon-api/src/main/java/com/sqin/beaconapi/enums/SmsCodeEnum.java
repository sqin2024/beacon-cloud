package com.sqin.beaconapi.enums;

import lombok.Getter;

/**
 * @Author Qin
 * @Date 2025/5/5 20:05
 * @Description
 **/
@Getter
public enum SmsCodeEnum {
    PARAMETER_ERROR(-10, "invalid parameter.")
    ;

    private Integer code;
    private String message;

    SmsCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
