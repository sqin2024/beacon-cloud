package com.sqin.strategy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Qin
 * @Date 2025/5/7 14:35
 * @Description
 **/
@RestController
public class TestController {

    @GetMapping("/test")
    public String getString() {
        return "test strategy";
    }

}
