package com.sqin.strategy.filter;

import com.sqin.common.constant.CacheConstant;
import com.sqin.common.model.StandardSubmit;
import com.sqin.strategy.client.BeaconCacheClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author Qin
 * @Date 2025/5/7 15:07
 * @Description
 **/
@Component
public class StrategyFilterContext {
    private static final String CLIENT_FILTERS = "clientFilters";

    @Autowired
    private Map<String, StrategyFilter> strategyFilterMap;

    @Autowired
    private BeaconCacheClient cacheClient;

    /**
     * 当前check方法用来管理校验链的顺序
     */
    public void strategy(StandardSubmit standardSubmit) {

        String filters = cacheClient.hgetString(CacheConstant.CLIENT_BUSINESS_PREFIX + standardSubmit.getApiKey(), CLIENT_FILTERS);

        //2、健壮性校验后，基于逗号分隔遍历
        String[] filterArray;
        if (filters != null && (filterArray = filters.split(",")).length > 0) {
            // 到这，filterArray不为null，并且有数据
            for (String strategy : filterArray) {
                //3、 遍历时，从stringStrategyFilterMap中获取到需要执行的校验信息，执行
                StrategyFilter strategyFilter = strategyFilterMap.get(strategy);
                if (strategyFilter != null) {
                    strategyFilter.strategy(standardSubmit);
                }
            }
        }
    }
}
