package com.sqin.beaconapi.advice;

import com.sqin.beaconapi.util.R;
import com.sqin.beaconapi.vo.ResultVO;
import com.sqin.common.exception.ApiException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author Qin
 * @Date 2025/5/6 22:27
 * @Description
 **/
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResultVO apiException(ApiException e) {
        return R.error(e);
    }

}
