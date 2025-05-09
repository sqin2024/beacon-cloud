package com.sqin.common.utils;

import com.sqin.common.model.StandardReport;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Qin
 * @Date 2025/5/9 16:08
 * @Description
 **/
public class CMPPDeliverMapUtil {

    private static ConcurrentHashMap<String, StandardReport> hashMap = new ConcurrentHashMap<>();


    public static void put(String sequence, StandardReport submit) {
        hashMap.put(sequence, submit);
    }

    public static StandardReport get(int sequence) {
        return hashMap.get(sequence + "");
    }

    public static StandardReport remove(int sequence) {
        return hashMap.remove(sequence + "");
    }

}
