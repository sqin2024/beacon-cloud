package com.sqin.tools.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author Qin
 * @Date 2025/5/6 20:10
 * @Description
 **/
@FeignClient("beacon-cache")
public interface CacheClient {

    @PostMapping(value = "/cache/hmset/{key}")
    void hmset(@PathVariable(value = "key") String key, @RequestBody Map<String, Object> map);

    @PostMapping(value = "/cache/set/{key}")
    void set(@PathVariable(value = "key") String key, @RequestParam(value = "value") Object value);

    @PostMapping(value = "/cache/sadd/{key}")
    void sadd(@PathVariable(value = "key") String key, @RequestBody Map<String, Object>... maps);
}
