package com.sqin.beaconapi.filter.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.sqin.beaconapi.client.BeaconCacheClient;
import com.sqin.beaconapi.filter.CheckFilter;
import com.sqin.common.constant.CacheConstant;
import com.sqin.common.enums.ExceptionEnums;
import com.sqin.common.exception.ApiException;
import com.sqin.common.model.StandardSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Qin
 * @Date 2025/5/5 18:06
 * @Description
 **/
@Service(value = "ip")
@Slf4j
public class IPCheckFilter implements CheckFilter {

    private static final String IP_ADDRESS = "ipAddress";

    @Autowired
    private BeaconCacheClient cacheClient;

    @Override
    public void check(StandardSubmit standardSubmit) {
        log.info("【接口模块-校验ip】 校验ing");
        // 根据apikey以及ipaddress，查询客户的白名单
        String ip = cacheClient.hgetString(CacheConstant.CLIENT_BUSINESS_PREFIX + standardSubmit.getApiKey(), IP_ADDRESS);
        standardSubmit.setIp(ip);

        //2. 如果IP白名单为null，直接放行
        if (StringUtils.isEmpty(ip) || ip.contains(standardSubmit.getRealIP())) {
            log.info("【接口模块-校验ip】  客户端请求IP合法！");
            return;
        }

        //3. IP白名单不为空，并且客户端请求不在IP报名单内
        log.info("【接口模块-校验ip】  请求的ip不在白名单内");
        throw new ApiException(ExceptionEnums.IP_NOT_WHITE);
    }

}
