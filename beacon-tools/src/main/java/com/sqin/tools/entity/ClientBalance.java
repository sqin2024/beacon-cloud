package com.sqin.tools.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClientBalance {

    private long id;
    private long clientId;
    private long balance;
    private java.sql.Timestamp created;
    private long createId;
    private java.sql.Timestamp updated;
    private long updateId;
    private long isDelete;
    private String extend1;
    private String extend2;
    private String extend3;
    private String extend4;

}
