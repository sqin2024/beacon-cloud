package com.sqin.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author Qin
 * @Date 2025/5/7 14:35
 * @Description
 **/
@RestController
public class TestController {

    @Autowired
    private ThreadPoolExecutor cmppSubmitPool;

    @GetMapping("/test")
    public String getString() {
        cmppSubmitPool.execute(() -> {
            System.out.println(Thread.currentThread().getName());
        });
        return "test sms gateway";
    }

}
