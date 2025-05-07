package com.sqin.strategy.filter.impl;

import com.sqin.common.constant.RabbitMQConstants;
import com.sqin.common.constant.SmsConstants;
import com.sqin.common.enums.ExceptionEnums;
import com.sqin.common.exception.StrategyException;
import com.sqin.common.model.StandardSubmit;
import com.sqin.strategy.filter.StrategyFilter;
import com.sqin.strategy.util.HutoolDFAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "hutoolDFADirtyWord")
@Slf4j
public class DirtyWordHutoolDFAStrategyFilter implements StrategyFilter {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void strategy(StandardSubmit submit) {
        log.info("【策略模块-敏感词校验】   DFA校验ing…………");
        //1、 获取短信内容
        String text = submit.getText();

        List<String> dirtyWords = HutoolDFAUtil.getDirtyWords(text);

        //4、 根据返回的set集合，判断是否包含敏感词
        if (dirtyWords != null && dirtyWords.size() > 0) {
            //5、 如果有敏感词，抛出异常 / 其他操作。。
            log.info("【策略模块-敏感词校验】   短信内容包含敏感词信息， dirtyWords = {}", dirtyWords);

            // 封装错误信息
            submit.setErrorMsg(ExceptionEnums.DIRTY_WORD.getMsg() + "dirtyWords = " + dirtyWords.toString());
            submit.setReportState(SmsConstants.REPORT_FAIL);

            rabbitTemplate.convertAndSend(RabbitMQConstants.SMS_WRITE_LOG, submit);

            // 还需要做其他处理
            throw new StrategyException(ExceptionEnums.DIRTY_WORD);
        }
    }
}
