package com.sqin.beaconapi.filter.impl;

import com.sqin.beaconapi.filter.CheckFilter;
import com.sqin.common.enums.ExceptionEnums;
import com.sqin.common.exception.ApiException;
import com.sqin.common.model.StandardSubmit;
import com.sqin.common.utils.PhoneFormatCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.alibaba.cloud.commons.lang.StringUtils.isEmpty;

/**
 * @Author Qin
 * @Date 2025/5/5 18:06
 * @Description
 **/
@Service(value = "mobile")
@Slf4j
public class MobileCheckFilter implements CheckFilter {

    @Override
    public void check(StandardSubmit standardSubmit) {
        log.info("【接口模块-校验手机号】   校验ing…………");
        String mobile = standardSubmit.getMobile();
        if (!isEmpty(mobile) && PhoneFormatCheckUtil.isChinaPhone(mobile)) {
            // 如果校验进来，代表手机号么得问题
            log.info("【接口模块-校验手机号】   手机号格式合法 mobile = {}", mobile);
            return;
        }
        log.info("【接口模块-校验手机号】   手机号格式不正确 mobile = {}", mobile);
        throw new ApiException(ExceptionEnums.ERROR_MOBILE);
    }

}
