package com.sqin.beaconapi.util;

import com.sqin.beaconapi.vo.ResultVO;

/**
 * @Author Qin
 * @Date 2025/5/5 19:17
 * @Description
 **/
public class R {

    public static ResultVO ok() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("接收成功");
        return resultVO;
    }

    public static ResultVO error(Integer code, String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(message);
        return resultVO;
    }

}
