package com.sqin.tools.entity;

import lombok.Data;

@Data
public class Channel {

    private long id;
    private String channelName; // 通道名称 如：北京移动、上海联通、深圳电信
    private long channelType; // 通道类型：0-三网 1-移动 2-联通 3-电信
    private String channelArea;
    private String channelAreaCode;
    private long channelPrice;
    private long channelProtocal;
    private String channelIp;
    private long channelPort;
    private String channelUsername;
    private String channelPassword;
    private String channelNumber;
    private long isAvailable;
    private java.sql.Timestamp created;
    private long createId;
    private java.sql.Timestamp updated;
    private long updateId;
    private long isDelete;

}
