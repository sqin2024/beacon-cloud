package com.sqin.strategy.filter.impl;

import com.sqin.common.model.StandardSubmit;
import com.sqin.strategy.filter.StrategyFilter;
import com.sqin.strategy.util.DFAUtil;
import com.sqin.strategy.util.HutoolDFAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service(value = "hutoolDFADirtyWord")
@Slf4j
public class DirtyWordHutoolDFAStrategyFilter implements StrategyFilter {

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
            // 还需要做其他处理
        }
    }
}
