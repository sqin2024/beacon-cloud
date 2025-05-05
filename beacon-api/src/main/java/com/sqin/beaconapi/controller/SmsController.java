package com.sqin.beaconapi.controller;

import com.sqin.beaconapi.enums.SmsCodeEnum;
import com.sqin.beaconapi.form.SingleSendForm;
import com.sqin.beaconapi.util.R;
import com.sqin.beaconapi.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResultVO singleSend(@RequestBody @Validated SingleSendForm singleSendForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
            log.info("invalid parameter, mes: " + defaultMessage);
            return R.error(SmsCodeEnum.PARAMETER_ERROR.getCode(), defaultMessage);
        }
        return R.ok();
    }

}
