package com.sqin.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author Qin
 * @Date 2025/5/8 22:45
 * @Description
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class BeaconSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeaconSearchApplication.class, args);
    }

}
