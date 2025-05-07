package com.sqin.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Qin
 * @Date 2025/5/5 20:30
 * @Description 各模块中传输的POJO对象
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardSubmit implements Serializable {

    /**
     * 针对当前短信的唯一标识
     * 雪花算法
     */
    private Long sequenceId;

    /**
     * 客户端ID
     * 查询缓存
     */
    private Long clientId;

    /**
     * 客户的ip白名单
     * 查询缓存
     */
    private String ip;

    /**
     * 客户业务内的Uid
     * 客户请求里传递
     */
    private String uid;

    /**
     * 目标手机号
     * 客户请求传递
     */
    private String mobile;

    /**
     * 短信内容的签名
     * 客户请求传递的，只需要从【】获取
     */
    private String sign;

    /**
     * 短信内容
     * 客户请求里的
     */
    private String text;

    /**
     * 短信发送时间
     * 当前系统时间
     */
    private LocalDateTime sendTime;

    /**
     * 当前短信的费用
     * 计算短信内容的文字，70字一条，超出部分，67字一条
     */
    private Long fee;

    /**
     * 目标手机号的运营商（策略模块）
     */
    private Integer operatorId;

    /**
     * 目标手机号的归属地区号（策略模块）
     */
    private Integer areaCode;

    /**
     * 目标手机号的归属地（策略模块）
     */
    private String area;

    /**
     * 通道下发的源号码 106988888888888（策略模块）
     */
    private String srcNumber;

    /**
     * 通道的id信息（策略模块）
     */
    private Long channelId;

    /**
     * 短信的发送状态，0 -等待， 1 - 成功， 2 - 失败
     * 默认情况下就是0
     */
    private int reportState;

    private String realIP;

    private String apiKey;

    private int state;

    private Long signId;

    private String errorMsg;
}
