package com.sqin.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author Qin
 * @Date 2025/5/5 20:30
 * @Description 各模块中传输的POJO对象
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardSubmit {

    /**
     * 针对当前短信的唯一标识
     */
    private Long sequenceId;

    /* 客户端ID */
    private Long clientId;

    /**
     * 客户的ip白名单
     */
    private String ip;

    /**
     * 客户业务内的Uid
     */
    private String uid;

    /**
     * 目标手机号
     */
    private String mobile;

    /**
     * 短信内容的签名
     */
    private String sign;

    /**
     * 短信内容
     */
    private String text;

    /**
     * 短信发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 当前短信的费用
     */
    private Long fee;

    /**
     * 目标手机号的运营商
     */
    private Integer operatorId;

    /**
     * 目标手机号的归属地区号
     */
    private Integer areaCode;

    /**
     * 目标手机号的归属地
     */
    private String area;

    /**
     * 通道下发的源号码 106988888888888
     */
    private String srcNumber;

    /**
     * 通道的id信息
     */
    private Long channelId;

    /**
     * 短信的发送状态，0 -等待， 1 - 成功， 2 - 失败
     */
    private int reportState;

}
