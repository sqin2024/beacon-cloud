package com.sqin.common.enums;

import lombok.Getter;

/**
 * @Author Qin
 * @Date 2025/5/6 22:14
 * @Description
 **/
@Getter
public enum ExceptionEnums {
    UNKNOWN_ERROR(-999, "未知异常"),
    ERROR_APIKEY(-1, "非法的apikey"),
    IP_NOT_WHITE(-2, "请求的ip不在白名单内"),
    ERROR_SIGN(-3, "无可用签名"),
    ERROR_TEMPLATE(-4, "无可用模板"),
    ERROR_MOBILE(-5, "手机号格式不正确"),
    BALANCE_NOT_ENOUGH(-6, "手客户余额不足"),
    SNOWFLAKE_OUT_OF_RANGE(-11, "雪花算法的机器id或服务id超出最大范围"),
    SNOWFLAKE_TIME_BACK(-12, "雪花算法的当前时间出现回拨"),
    DIRTY_WORD(-13, "当前短信内容里包含敏感信息"),
    BLACK_GLOBAL(-14, "当前手机号属于全局黑名单"),
    BLACK_CLIENT(-15, "当前手机号属于客户黑名单"),
    ONE_MINUTE_LIMIT(-16, "一分钟只能发送一条短信"),
    ONE_HOUR_LIMIT(-17, "一小时只能发送三条短信"),
    NO_CHANNEL(-18, "没有可以发送渠道"),
    INDEX_ERROR(-19, "添加文档信息失败"),
    ;

    private Integer code;
    private String msg;

    ExceptionEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
