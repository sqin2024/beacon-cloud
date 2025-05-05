package com.sqin.beaconapi.controller;

import com.sqin.beaconapi.filter.CheckFilterContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private CheckFilterContext checkFilterContext;

    @GetMapping("/api/test")
    public String test() {
        System.out.println("==============");
        checkFilterContext.check(new Object());
        return "test beacon api";
    }

}
