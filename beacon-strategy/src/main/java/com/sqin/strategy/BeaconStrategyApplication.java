package com.sqin.strategy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author Qin
 * @Date 2025/5/7 14:31
 * @Description
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BeaconStrategyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeaconStrategyApplication.class,args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
