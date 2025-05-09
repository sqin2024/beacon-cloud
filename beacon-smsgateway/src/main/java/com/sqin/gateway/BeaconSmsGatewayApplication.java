package com.sqin.gateway;

import cn.hippo4j.core.enable.EnableDynamicThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author Qin
 * @Date 2025/5/9 14:14
 * @Description
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableDynamicThreadPool
public class BeaconSmsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeaconSmsGatewayApplication.class, args);
    }
}
