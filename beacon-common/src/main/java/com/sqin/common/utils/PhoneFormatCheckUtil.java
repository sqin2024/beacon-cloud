package com.sqin.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Qin
 * @Date 2025/5/7 0:44
 * @Description
 **/
public class PhoneFormatCheckUtil {

    /**
     * 国内手机号的正则表达式
     */
    private final static Pattern CHINA_PATTERN = Pattern.compile("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$");

    /**
     * 根据正则校验手机号是否合法
     * @param number
     * @return
     */
    public static boolean isChinaPhone(String number){
        Matcher matcher = CHINA_PATTERN.matcher(number);
        return matcher.matches();
    }

}
