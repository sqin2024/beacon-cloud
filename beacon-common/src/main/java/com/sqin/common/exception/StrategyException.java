package com.sqin.common.exception;

import com.sqin.common.enums.ExceptionEnums;
import lombok.Getter;

/**
 * @Author Qin
 * @Date 2025/5/7 22:14
 * @Description
 **/
@Getter
public class StrategyException extends RuntimeException {

    private Integer code;

    public StrategyException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public StrategyException(ExceptionEnums enums) {
        super(enums.getMsg());
        this.code = enums.getCode();
    }

}
