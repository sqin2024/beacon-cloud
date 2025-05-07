package com.sqin.strategy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author Qin
 * @Date 2025/5/7 14:31
 * @Description
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class BeaconStrategyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeaconStrategyApplication.class,args);
    }

}
