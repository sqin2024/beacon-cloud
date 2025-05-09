package com.sqin.push;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author Qin
 * @Date 2025/5/9 10:04
 * @Description
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class BeaconPushApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeaconPushApplication.class, args);
    }

}
