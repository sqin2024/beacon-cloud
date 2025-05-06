package com.sqin.beaconapi.filter;

import com.sqin.common.model.StandardSubmit;

/**
 * @Author Qin
 * @Date 2025/5/5 18:05
 * @Description 策略模式的父接口
 **/
public interface CheckFilter {

    void check(StandardSubmit standardSubmit);

}
