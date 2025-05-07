package com.sqin.strategy.filter;

import com.sqin.common.model.StandardSubmit;

public interface StrategyFilter {

    /**
     * 校验！！！！
     *
     * @param standardSubmit
     */
    void strategy(StandardSubmit standardSubmit);
}