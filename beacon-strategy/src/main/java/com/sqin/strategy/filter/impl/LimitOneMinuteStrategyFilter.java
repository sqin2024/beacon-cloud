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

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @Author Qin
 * @Date 2025/5/8 14:38
 * @Description
 **/
@Service("limitOneMinute")
@Slf4j
public class LimitOneMinuteStrategyFilter implements StrategyFilter {


    private final String UTC = "+8";

    private final long ONE_MINUTE = 60 * 1000 - 1;

    @Autowired
    private BeaconCacheClient cacheClient;

    @Autowired
    private ErrorSendMsgUtil sendMsgUtil;

    @Override
    public void strategy(StandardSubmit submit) {
        //1、基于submit获取短信的发送时间
        LocalDateTime sendTime = submit.getSendTime();
        //2、基于LocalDateTime获取到时间的毫秒值
        long sendTimeMilli = sendTime.toInstant(ZoneOffset.of(UTC)).toEpochMilli();

        //3、基于submit获取客户标识以及手机号信息
        Long clientId = submit.getClientId();
        String mobile = submit.getMobile();

        //4、优先将当前短信发送信息插入到Redis的ZSet结构中 zadd
        String key = CacheConstant.LIMIT_MINUTES + clientId + CacheConstant.SEPARATE + mobile;
        Boolean addOk = cacheClient.zadd(key, sendTimeMilli, sendTimeMilli);

        //5、如果查询失败，直接告辞，有并发情况，60s不能发送两条，直接告辞
        if (!addOk) {
            log.info("【策略模块-一分钟限流策略】  插入失败！ 满足一分钟限流规则，无法发送！");
            submit.setErrorMsg(ExceptionEnums.ONE_MINUTE_LIMIT + ",mobile = " + mobile);
            sendMsgUtil.sendWriteLog(submit);
            sendMsgUtil.sendPushReport(submit);
            throw new StrategyException(ExceptionEnums.ONE_MINUTE_LIMIT);
        }

        //6、基于zrangebyscore查询1分钟直接，是否只有当前查询的发送短信信息
        long start = sendTimeMilli - ONE_MINUTE;
        int count = cacheClient.zRangeByScoreCount(key, Double.parseDouble(start + ""), Double.parseDouble(sendTimeMilli + ""));

        //7、如果大于等于2条短信信息，达到了60s一条的短信限流规则，直接告辞。
        if (count > 1) {
            // 一分钟之前，发送过短信，限流规则生效
            log.info("【策略模块-一分钟限流策略】  插入失败！ 满足一分钟限流规则，无法发送！");
            cacheClient.zRemove(key, sendTimeMilli + "");
            submit.setErrorMsg(ExceptionEnums.ONE_MINUTE_LIMIT + ", mobile = " + mobile);
            sendMsgUtil.sendWriteLog(submit);
            sendMsgUtil.sendPushReport(submit);
            throw new StrategyException(ExceptionEnums.ONE_MINUTE_LIMIT);
        }

        log.info("【策略模块-一分钟限流策略】  一分钟限流规则通过，可以发送！");
    }
}
