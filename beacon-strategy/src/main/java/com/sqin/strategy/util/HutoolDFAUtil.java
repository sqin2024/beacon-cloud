package com.sqin.strategy.util;

import cn.hutool.dfa.WordTree;
import com.sqin.common.constant.CacheConstant;
import com.sqin.strategy.client.BeaconCacheClient;

import java.util.List;
import java.util.Set;

/**
 * @Author Qin
 * @Date 2025/5/7 22:00
 * @Description
 **/
public class HutoolDFAUtil {

    private static WordTree wordTree = new WordTree();

    /**
     * 初始化敏感词树
     */
    static {
        // 获取Spring容器中的cacheClient
        BeaconCacheClient cacheClient = (BeaconCacheClient) SpringUtil.getBeanByClass(BeaconCacheClient.class);
        // 获取存储在Redis中的全部敏感词
        Set<String> dirtyWords = cacheClient.smember(CacheConstant.DIRTY_WORD);
        // 调用create，将dfaMap的敏感词树构建
        wordTree.addWords(dirtyWords);
    }

    public static List<String> getDirtyWords(String text) {
        return wordTree.matchAll(text);
    }

}
