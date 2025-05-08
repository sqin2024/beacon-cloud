package com.sqin.strategy.filter.impl;

import com.sqin.common.enums.ExceptionEnums;
import com.sqin.common.exception.StrategyException;
import com.sqin.common.model.StandardSubmit;
import com.sqin.strategy.filter.StrategyFilter;
import com.sqin.strategy.util.ErrorSendMsgUtil;
import com.sqin.strategy.util.HutoolDFAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "hutoolDFADirtyWord")
@Slf4j
public class DirtyWordHutoolDFAStrategyFilter implements StrategyFilter {

    @Autowired
    private ErrorSendMsgUtil errorSendMsgUtil;

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

            // 发送写日志
            errorSendMsgUtil.sendWriteLog(submit, dirtyWords);

            // ================================发送状态报告的消息前，需要将report对象数据封装================================
            errorSendMsgUtil.sendPushReport(submit);

            // 还需要做其他处理
            throw new StrategyException(ExceptionEnums.DIRTY_WORD);
        }

        log.info("【策略模块-敏感词校验】  校验通过，没有敏感词 ");
    }
}
