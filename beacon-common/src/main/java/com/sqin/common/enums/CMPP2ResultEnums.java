package com.sqin.common.enums;

import lombok.Getter;

/**
 * @Author Qin
 * @Date 2025/5/9 22:30
 * @Description
 **/
@Getter
public enum CMPP2ResultEnums {
    OK(0, "正确"),
    MESSAGE_BUILD_ERROR(1, "i消息结构错"),
    COMMAND_WORD_ERROR(2, "命令字错"),
    MESSAGE_SEQUENCE_ERROR(3, "消息序号重复"),
    MESSAGE_LENGTH_ERROR(4, "消息长度错"),
    ;

    private Integer result;

    private String msg;

    CMPP2ResultEnums(Integer result, String msg) {
        this.result = result;
        this.msg = msg;
    }
}
