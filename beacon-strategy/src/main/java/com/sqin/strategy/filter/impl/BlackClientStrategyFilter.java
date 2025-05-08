package com.sqin.strategy.filter.impl;

import com.sqin.common.constant.CacheConstant;
import com.sqin.common.enums.ExceptionEnums;
import com.sqin.common.exception.StrategyException;
import com.sqin.common.model.StandardSubmit;
import com.sqin.strategy.client.BeaconCacheClient;
import com.sqin.strategy.filter.StrategyFilter;
import com.sqin.strategy.util.ErrorSendMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "blackClient")
@Slf4j
public class BlackClientStrategyFilter implements StrategyFilter {

    @Autowired
    private ErrorSendMsgUtil sendMsgUtil;

    @Autowired
    private BeaconCacheClient cacheClient;

    // 黑名单的默认value
    private final String TRUE = "1";

    @Override
    public void strategy(StandardSubmit submit) {
        log.info("【策略模块-客户级别黑名单校验】   校验ing…………");

        // 获得客户手机号以及客户的id
        String mobile = submit.getMobile();
        Long clientId = submit.getClientId();

        String value = cacheClient.getString(CacheConstant.BLACK + clientId + CacheConstant.SEPARATE + mobile);

        if (TRUE.equals(value)) {
            log.info("【策略模块-客户级别黑名单校验】   当前发送的手机号是客户黑名单！ mobile = {}", mobile);
            submit.setErrorMsg(ExceptionEnums.BLACK_CLIENT + ",mobile = " + mobile);
            sendMsgUtil.sendWriteLog(submit);
            sendMsgUtil.sendPushReport(submit);
            throw new StrategyException(ExceptionEnums.BLACK_CLIENT);
        }

        //4、不是1，正常结束
        log.info("【策略模块-客户级别黑名单校验】   当前手机号不是客户黑名单！ ");
    }

}
