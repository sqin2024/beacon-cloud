package com.sqin.strategy.filter.impl;

import com.sqin.common.model.StandardSubmit;
import com.sqin.strategy.filter.StrategyFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author Qin
 * @Date 2025/5/7 15:15
 * @Description
 **/

@Service(value = "route")
@Slf4j
public class RouteStrategyFilter implements StrategyFilter {
    @Override
    public void strategy(StandardSubmit standardSubmit) {

        log.info("【策略模块-路由】   校验ing…………");
    }
}
