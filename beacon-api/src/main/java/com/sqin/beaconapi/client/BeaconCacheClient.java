package com.sqin.beaconapi.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author Qin
 * @Date 2025/5/6 17:10
 * @Description
 **/
@FeignClient("beacon-cache")
public interface BeaconCacheClient {


}
