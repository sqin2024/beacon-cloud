package com.sqin.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author Qin
 * @Date 2025/5/5 21:29
 * @Description
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class BeaconCacheApplication {

    public static void main(String[] args) {

        SpringApplication.run(BeaconCacheApplication.class);

    }

}
