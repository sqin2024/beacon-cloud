package com.sqin.beaconapi.filter.impl;

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
@Service(value = "template")
@Slf4j
public class TemplateCheckFilter implements CheckFilter {
    /**
     * 模板内容中的具体模板信息
     */
    private static final String TEMPLATE_TEXT = "templateText";

    private static final String TEMPLATE_PLACEHOLDER = "#";

    @Autowired
    private BeaconCacheClient cacheClient;

    @Override
    public void check(StandardSubmit standardSubmit) {
        log.info("【接口模块-校验模板】   校验ing…………");
        // 1、从submit中获取到短信内容，签名信息，签名id
        String text = standardSubmit.getText();
        String sign = standardSubmit.getSign();
        Long signId = standardSubmit.getSignId();
        // 2、将短信内容中的签名直接去掉，获取短信具体内容

        text = text.replace(ApiConstant.SIGN_PREFIX + sign + ApiConstant.SIGN_SUFFIX, "");
        // 3、从缓存中获取到签名id绑定的所有模板
        Set<Map> templates = cacheClient.smember(CacheConstant.CLIENT_TEMPLATE_PREFIX + signId);
        // 4、在tempaltes不为null时，遍历签名绑定的所有模板信息
        if (templates != null && templates.size() > 0) {
            for (Map template : templates) {
                // 4.1 将模板内容和短信具体内容做匹配-true-匹配成功
                String templateText = (String) template.get(TEMPLATE_TEXT);
                if (text.equals(templateText)) {
                    // 短信具体内容和模板是匹配的。
                    log.info("【接口模块-校验模板】   校验模板通过 templateText = {}", templateText);
                    return;
                }
                // 4.2 判断模板中是否只包含一个变量，如果是，直接让具体短信内容匹配前缀和后缀
                // 例子：您的验证码是123434。如非本人操作，请忽略本短信
                // 例子：您的验证码是#code#。如非本人操作，请忽略本短信
                if (templateText != null && templateText.contains(TEMPLATE_PLACEHOLDER)
                        && templateText.length() - templateText.replaceAll(TEMPLATE_PLACEHOLDER, "").length() == 2) {
                    // 可以确认模板不为空，并且包含#符号，而且#符号有2个，代表是一个占位符（变量）。
                    // 获取模板撇去占位符之后的前缀和后缀
                    String templateTextPrefix = templateText.substring(0, templateText.indexOf(TEMPLATE_PLACEHOLDER));
                    String templateTextSuffix = templateText.substring(templateText.lastIndexOf(TEMPLATE_PLACEHOLDER) + 1);
                    // 判断短信的具体内容是否匹配前缀和后缀
                    if (text.startsWith(templateTextPrefix) && text.endsWith(templateTextSuffix)) {
                        // 当前的短信内容匹配短信模板
                        log.info("【接口模块-校验模板】   校验模板通过 templateText = {}", templateText);
                        return;
                    }
                }
            }
        }
        // 5、 模板校验失败
        log.info("【接口模块-校验模板】   无可用模板 text = {}", text);
        throw new ApiException(ExceptionEnums.ERROR_TEMPLATE);
    }

}
