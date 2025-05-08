package com.sqin.common.exception;

import com.sqin.common.enums.ExceptionEnums;

/**
 * @Author Qin
 * @Date 2025/5/8 23:23
 * @Description
 **/
public class SearchException extends RuntimeException{

    private Integer code;

    public SearchException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public SearchException(ExceptionEnums enums) {
        super(enums.getMsg());
        this.code = enums.getCode();
    }

}
