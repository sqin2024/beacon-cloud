package com.sqin.tools.entity;

import lombok.Data;

@Data
public class ClientChannel {

    private long clientId;

    private long channelId;

    private long clientChannelWeight;

    private String clientChannelNumber;

    private long isAvailable;

}
