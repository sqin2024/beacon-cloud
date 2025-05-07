package com.sqin.beaconapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {
        "com.sqin.beaconapi",
        "com.sqin.common"
})
public class BeaconApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeaconApiApplication.class);
    }

}
