package com.sqin.beaconapi.vo;

import lombok.Data;

/**
 * @Author Qin
 * @Date 2025/5/5 19:15
 * @Description
 **/
@Data
public class ResultVO {

    private Integer code;

    private String msg;

    private Integer count;

    private Long fee;

    private String uid;

    private String sid;
}
