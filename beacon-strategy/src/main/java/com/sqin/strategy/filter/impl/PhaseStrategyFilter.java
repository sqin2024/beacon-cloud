package com.sqin.strategy.filter.impl;

import com.sqin.common.model.StandardSubmit;
import com.sqin.strategy.filter.StrategyFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author Qin
 * @Date 2025/5/7 15:03
 * @Description
 **/
@Service(value = "phase")
@Slf4j
public class PhaseStrategyFilter implements StrategyFilter {
    @Override
    public void strategy(StandardSubmit standardSubmit) {
        log.info("【策略模块-号段补齐】   校验ing…………");
    }
}
