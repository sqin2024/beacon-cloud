package com.sqin.cache.controller;

import com.msb.framework.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author Qin
 * @Date 2025/5/5 22:15
 * @Description
 **/
@RestController
public class TestController {

    @Autowired
    private RedisClient redisClient;
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/test/set/{key}")
    public String set(@PathVariable("key") String key, @RequestBody Map map) {
//        redisTemplate.opsForHash().putAll(key, map);
        redisClient.hSet(key, map);
        return "ok";
    }

    @GetMapping("/test/get/{key}")
    public Map get(@PathVariable("key") String key) {
//        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        Map<String, Object> stringObjectMap = redisClient.hGetAll(key);
        return stringObjectMap;
    }

}
