package com.sqin.beaconapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BeaconApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeaconApiApplication.class);
    }

}
