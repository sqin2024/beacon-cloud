package com.sqin.strategy.filter.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.sqin.common.constant.CacheConstant;
import com.sqin.common.model.StandardSubmit;
import com.sqin.strategy.client.BeaconCacheClient;
import com.sqin.strategy.filter.StrategyFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Qin
 * @Date 2025/5/8 14:08
 * @Description
 **/
@Service(value = "transfer")
@Slf4j
public class TransferStrategyFilter implements StrategyFilter {

    // 代表携号转网了！
    private final Boolean TRANSFER = true;

    @Autowired
    private BeaconCacheClient cacheClient;

    @Override
    public void strategy(StandardSubmit standardSubmit) {
        log.info("【策略模块-携号转网策略】   ing…………");
        //1、获取用户手机号
        String mobile = standardSubmit.getMobile();

        //2、直接基于Redis查询携号转网信息
        String value = cacheClient.getString(CacheConstant.TRANSFER + mobile);

        //3、如果存在携号转网，设置运营商信息
        if (!StringUtils.isEmpty(value)) {
            // 代表携号转网了
            standardSubmit.setOperatorId(Integer.valueOf(value));
            standardSubmit.setIsTransfer(TRANSFER);
            log.info("【策略模块-携号转网策略】   当前手机号携号转网了！");
            return;
        }

        log.info("【策略模块-携号转网策略】   嘛事没有！");
    }

}
