package com.sqin.common.exception;

import com.sqin.common.enums.ExceptionEnums;
import lombok.Getter;

/**
 * @Author Qin
 * @Date 2025/5/6 22:11
 * @Description
 **/
@Getter
public class ApiException extends RuntimeException {

    private Integer code;

    public ApiException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public ApiException(ExceptionEnums enums) {
        super(enums.getMsg());
        this.code = enums.getCode();
    }

}
