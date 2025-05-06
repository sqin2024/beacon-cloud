package com.sqin.beaconapi.filter.impl;

import com.sqin.beaconapi.client.BeaconCacheClient;
import com.sqin.beaconapi.filter.CheckFilter;
import com.sqin.common.constant.CacheConstant;
import com.sqin.common.enums.ExceptionEnums;
import com.sqin.common.exception.ApiException;
import com.sqin.common.model.StandardSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author Qin
 * @Date 2025/5/5 18:06
 * @Description
 **/
@Service(value = "apikey")
@Slf4j
public class ApiKeyCheckFilter implements CheckFilter {

    @Autowired
    private BeaconCacheClient beaconCacheClient;

    @Override
    public void check(StandardSubmit standardSubmit) {
        log.info("【接口模块-校验Apikey】 校验ing");
        // 从redis中查询数据
        Map clientBusiness = beaconCacheClient.hGetAll(CacheConstant.CLIENT_BUSINESS_PREFIX + standardSubmit.getApiKey());

        // 如果查询不到数据，直接抛出异常
        if (clientBusiness == null || clientBusiness.size() == 0) {
            log.info("【接口模块-校验Apikey】 非法apikey = {}", standardSubmit.getApiKey());
            throw new ApiException(ExceptionEnums.ERROR_APIKEY);
        }

        // 如果查到client_business消息
        standardSubmit.setClientId(Long.parseLong(clientBusiness.get("id") + ""));
        log.info("【接口模块-校验apikey】 查询到客户信息 clientBusiness = {}", clientBusiness);
    }

}
