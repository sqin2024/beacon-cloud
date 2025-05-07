package com.sqin.beaconapi.controller;

import com.sqin.beaconapi.enums.SmsCodeEnum;
import com.sqin.beaconapi.filter.CheckFilterContext;
import com.sqin.beaconapi.form.SingleSendForm;
import com.sqin.beaconapi.util.R;
import com.sqin.beaconapi.vo.ResultVO;
import com.sqin.common.constant.RabbitMQConstants;
import com.sqin.common.model.StandardSubmit;
import com.sqin.common.utils.SnowFlakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Qin
 * @Date 2025/5/5 19:10
 * @Description
 **/
@RestController
@RequestMapping("/sms")
@Slf4j
@RefreshScope
public class SmsController {

    private static final String UNKNOWN = "UNKNOWN";

    private static final String X_FORWARDED_FOR = "x-forwarded-for";

    @Value("${headers}")
    private String headers;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SnowFlakeUtil snowFlakeUtil;

    @Autowired
    private CheckFilterContext checkFilterContext;

    @PostMapping(value = "/single_send", produces = "application/json;charset=utf-8")
    public ResultVO singleSend(@RequestBody @Validated SingleSendForm singleSendForm, BindingResult bindingResult, HttpServletRequest request) {
        // parameter validate.
        if (bindingResult.hasErrors()) {
            String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
            log.info("invalid parameter, mes: " + defaultMessage);
            return R.error(SmsCodeEnum.PARAMETER_ERROR.getCode(), defaultMessage);
        }

        // build standardSubmit, do validate
        String realIP = getRealIP(request);
        StandardSubmit standardSubmit = new StandardSubmit();
        standardSubmit.setRealIP(realIP);
        standardSubmit.setApiKey(singleSendForm.getApiKey());
        standardSubmit.setMobile(singleSendForm.getMobile());
        standardSubmit.setText(singleSendForm.getText());
        standardSubmit.setUid(singleSendForm.getUid());
        standardSubmit.setState(singleSendForm.getState());

        checkFilterContext.check(standardSubmit);

        long nextId = snowFlakeUtil.nextId();
        standardSubmit.setSequenceId(nextId);

        // send message to mq
        rabbitTemplate.convertAndSend(RabbitMQConstants.SMS_PRE_SEND, standardSubmit, new CorrelationData(standardSubmit.getSequenceId().toString()));
        return R.ok();
    }

    /**
     * 获取客户端真实的IP地址
     *
     * @param request
     * @return
     */
    private String getRealIP(HttpServletRequest request) {
        //1. 声明返回的ip地址
        String ip;

        //2. 遍历请求头，并且通过req获取ip地址
        for (String header : headers.split(",")) {
            // 健壮性校验
            if (!StringUtils.isEmpty(header)) {
                // 基于req获取ip地址
                ip = request.getHeader(header);
                // 如果获取到的ip不为null，不为空串，并且不为unknow，就可以返回
                if (!StringUtils.isEmpty(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
                    // 判断请求头是否是x-forwarded-for
                    if (X_FORWARDED_FOR.equalsIgnoreCase(header) && ip.indexOf(",") > 0) {
                        ip = ip.substring(0, ip.indexOf(","));
                    }
                    // 返回IP地址
                    return ip;
                }
            }
        }

        //3. 如果请求头都没有获取到IP地址，直接基于传统的方式获取一个IP
        return request.getRemoteAddr();
    }

}
