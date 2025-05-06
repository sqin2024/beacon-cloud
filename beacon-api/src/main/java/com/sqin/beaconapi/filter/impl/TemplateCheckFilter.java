package com.sqin.beaconapi.filter.impl;

import com.sqin.beaconapi.filter.CheckFilter;
import com.sqin.common.model.StandardSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author Qin
 * @Date 2025/5/5 18:06
 * @Description
 **/
@Service(value = "template")
@Slf4j
public class TemplateCheckFilter implements CheckFilter {

    @Override
    public void check(StandardSubmit standardSubmit) {
        log.info("【接口模块-校验模板】 校验ing");
    }

}
