package com.sqin.beaconapi.controller;

import com.sqin.beaconapi.enums.SmsCodeEnum;
import com.sqin.beaconapi.form.SingleSendForm;
import com.sqin.beaconapi.util.R;
import com.sqin.beaconapi.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
public class SmsController {

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

        // send to MQ.
        return R.ok();
    }

    /**
     * 获取客户端真实的IP地址
     * @param request
     * @return
     */
    private String getRealIP(HttpServletRequest request) {
        //0. 声明真实IP地址
        String ip;

        //1. 基于x-forwarded-for请求头获取IP地址
        String ips = request.getHeader("x-forwarded-for");
        //  直接基于第一个,的位置，截取需要的IP地址
        if (StringUtils.isEmpty(ips) || "unknow".equalsIgnoreCase(ips)) {
            if (ips.contains(",")) {
                return ips.substring(0, ips.indexOf(","));
            } else {
                return ips;
            }
        }

        // 2. 基于请求头获取IP地址，基于request请求头获取信息时，除了null和空串外，还有可能拿到unknow，
        ip = request.getHeader("x-real-ip");
        if (StringUtils.isEmpty(ip) || "unknow".equalsIgnoreCase(ip)) {
            // x-real-ip没拿到，考虑一下其他的代理服务器
            //3.  Apache的服务器，请求头中携带真实IP的名称是 proxy-client-ip
            ip = request.getHeader("proxy-client-ip");
        }

        //4. 如果real没有拿到，判断apache是否拿到了。
        if (StringUtils.isEmpty(ip) || "unknow".equalsIgnoreCase(ip)) {
            // 5. 如果Apache服务器没拿到，考虑一手WebLogic, wl-proxy-client-ip
            ip = request.getHeader("wl-proxy-client-ip");
        }

        //6. 判断WebLogic有木有拿到IP
        if (StringUtils.isEmpty(ip) || "unknow".equalsIgnoreCase(ip)) {
            //7. 基于其他的代理服务器的方式获取请求头的IP地址
            ip = request.getHeader("http_client_ip");
        }
        //8. 如果上诉方式都获取不到，
        if (StringUtils.isEmpty(ip) || "unknow".equalsIgnoreCase(ip)) {
            // 9. 基于传统方式获取IP
            ip = request.getRemoteAddr();
        }
        //10. 返回
        return ip;
    }

}
