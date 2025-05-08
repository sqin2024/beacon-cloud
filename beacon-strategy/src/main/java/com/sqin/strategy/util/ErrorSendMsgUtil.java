package com.sqin.strategy.util;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.sqin.common.constant.CacheConstant;
import com.sqin.common.constant.RabbitMQConstants;
import com.sqin.common.constant.SmsConstants;
import com.sqin.common.enums.ExceptionEnums;
import com.sqin.common.model.StandardReport;
import com.sqin.common.model.StandardSubmit;
import com.sqin.strategy.client.BeaconCacheClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Qin
 * @Date 2025/5/8 10:31
 * @Description
 **/
@Component
public class ErrorSendMsgUtil {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BeaconCacheClient cacheClient;

    /**
     * 策略模块校验未通过，发送写日志操作
     *
     * @param submit
     * @param dirtyWords
     */
    public void sendWriteLog(StandardSubmit submit, List<String> dirtyWords) {
        submit.setErrorMsg(ExceptionEnums.DIRTY_WORD.getMsg() + "dirtyWords = " + dirtyWords.toString());
        submit.setReportState(SmsConstants.REPORT_FAIL);
        // 发送消息到写日志队列
        rabbitTemplate.convertAndSend(RabbitMQConstants.SMS_WRITE_LOG, submit);
    }

    /**
     * 策略模块校验未通过，发送状态报告操作
     */
    public void sendPushReport(StandardSubmit submit) {
        // 查询当前客户的isCallback
        Integer isCallback = cacheClient.hgetInteger(CacheConstant.CLIENT_BUSINESS_PREFIX + submit.getApiKey(), "isCallback");
        // 查看是否需要给客户一个回调
        if (isCallback == 1) {
            // 如果需要回调，再查询客户的回调地址
            String callbackUrl = cacheClient.hgetString(CacheConstant.CLIENT_BUSINESS_PREFIX + submit.getApiKey(), "callbackUrl");
            // 如果回调地址不为空。
            if (!StringUtils.isEmpty(callbackUrl)) {
                // 封装客户的报告推送的信息，开始封装StandardReport
                StandardReport report = new StandardReport();
                BeanUtils.copyProperties(submit, report);
                report.setIsCallback(isCallback);
                report.setCallbackUrl(callbackUrl);
                // 发送消息到RabbitMQ
                rabbitTemplate.convertAndSend(RabbitMQConstants.SMS_PUSH_REPORT, report);
            }

        }
    }
}
