package com.sqin.beaconapi.filter.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.sqin.beaconapi.client.BeaconCacheClient;
import com.sqin.beaconapi.filter.CheckFilter;
import com.sqin.common.constant.ApiConstant;
import com.sqin.common.constant.CacheConstant;
import com.sqin.common.enums.ExceptionEnums;
import com.sqin.common.exception.ApiException;
import com.sqin.common.model.StandardSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @Author Qin
 * @Date 2025/5/5 18:06
 * @Description
 **/
@Service(value = "sign")
@Slf4j
public class SignCheckFilter implements CheckFilter {

    public static final int SIGN_START_INDEX = 1;

    public static final String CLIENT_SIGN_INFO = "signInfo";

    @Autowired
    private BeaconCacheClient cacheClient;

    @Override
    public void check(StandardSubmit standardSubmit) {
        log.info("【接口模块-校验sign】 校验ing");

        //1. 判断短信内容是否携带了【】
        String text = standardSubmit.getText();
        if (!text.startsWith(ApiConstant.SIGN_PREFIX) || !text.contains(ApiConstant.SIGN_SUFFIX)) {
            log.info("【接口模块-校验签名】   无可用签名 text = {}", text);
            throw new ApiException(ExceptionEnums.ERROR_SIGN);
        }

        //2. 将短信内容中的签名截取出来
        String sign = text.substring(SIGN_START_INDEX, text.indexOf(ApiConstant.SIGN_SUFFIX));
        if (StringUtils.isEmpty(sign)) {
            log.info("【接口模块-校验签名】   无可用签名 text = {}", text);
            throw new ApiException(ExceptionEnums.ERROR_SIGN);
        }

        //3. 从缓存中查询出客户绑定的签名
        Set<Map> set = cacheClient.smember(CacheConstant.CLIENT_SIGN_PREFIX + standardSubmit.getClientId());
        if(set == null || set.size() == 0){
            log.info("【接口模块-校验签名】   无可用签名 text = {}",text);
            throw new ApiException(ExceptionEnums.ERROR_SIGN);
        }

        //4. 判断~
        for (Map map : set) {
            if(sign.equals(map.get(CLIENT_SIGN_INFO))){
                log.info("【接口模块-校验签名】   找到匹配的签名 sign = {}",sign);
                return;
            }
        }

        //5. 到这，说明没有匹配的签名
        log.info("【接口模块-校验签名】   无可用签名 text = {}",text);
        throw new ApiException(ExceptionEnums.ERROR_SIGN);
    }

}
