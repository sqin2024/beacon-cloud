package com.sqin.tools.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClientTemplate {

    private long id;
    private long signId;
    private String templateText;
    private long templateType;
    private long templateState;
    private long useId;
    private String useWeb;
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
