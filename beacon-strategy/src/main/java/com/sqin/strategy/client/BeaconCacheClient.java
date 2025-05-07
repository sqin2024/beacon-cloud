package com.sqin.strategy.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "beacon-cache")
public interface BeaconCacheClient {

    @GetMapping("/cache/hget/{key}/{field}")
    String hgetString(@PathVariable(value = "key") String key, @PathVariable(value = "field") String field);

    @GetMapping(value = "/cache/get/{key}")
    String getString(@PathVariable(value = "key") String key);

}
