package com.sqin.common.utils;

import com.sqin.common.model.StandardSubmit;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Qin
 * @Date 2025/5/9 16:08
 * @Description
 **/
public class CMPPSubmitRepoMapUtil {

    private static ConcurrentHashMap<String, StandardSubmit> hashMap = new ConcurrentHashMap<>();


    public static void put(int sequence, StandardSubmit submit) {
        hashMap.put(sequence + "", submit);
    }

    public static StandardSubmit get(int sequence) {
        return hashMap.get(sequence + "");
    }

    public static StandardSubmit remove(int sequence) {
        return hashMap.remove(sequence + "");
    }

}
