package com.sqin.cache.controller;

import com.msb.framework.redis.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * @Author Qin
 * @Date 2025/5/6 20:14
 * @Description
 **/
@RestController
@Slf4j
public class CacheController {

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping(value = "/cache/hmset/{key}")
    public void hmset(@PathVariable(value = "key") String key, @RequestBody Map<String, Object> map) {
        log.info("[缓存模块] hmset方法, 存储key = {}, value = {}", key, map);
        redisClient.hSet(key, map);
    }

    @PostMapping(value = "/cache/set/{key}")
    public void set(@PathVariable(value = "key") String key, @RequestParam(value = "value") Object value) {
        log.info("[缓存模块] set方法, 存储key = {}, value = {}", key, value);
        redisClient.set(key, value);
    }

    @GetMapping(value = "/cache/get/{key}")
    public Object get(@PathVariable(value = "key") String key) {
        log.info("[缓存模块] get方法, 存储key = {}", key);
        return redisClient.get(key);
    }

    @PostMapping(value = "/cache/sadd/{key}")
    public void sadd(@PathVariable(value = "key") String key, @RequestBody Map<String, Object>... value) {
        log.info("[缓存模块] sadd方法, 存储key = {}, value = {}", key, value);
        redisClient.sAdd(key, value);
    }

    @GetMapping("/cache/hgetall/{key}")
    public Map hGetAll(@PathVariable(value = "key") String key) {
        log.info("【缓存模块】 hGetAll方法，获取key ={} 的数据", key);
        Map<String, Object> value = redisClient.hGetAll(key);
        log.info("【缓存模块】 hGetAll方法，获取key ={} 的数据 value = {}", key, value);
        return value;
    }

    @GetMapping("/cache/hget/{key}/{field}")
    public Object hget(@PathVariable(value = "key") String key, @PathVariable(value = "field") String field) {
        log.info("【缓存模块】 hGet方法，获取key ={},field={} 的数据", key, field);
        Object value = redisClient.hGet(key, field);
        log.info("【缓存模块】 hGet方法，获取key ={},field={} 的数据 value = {}", key, field, value);
        return value;
    }

    @GetMapping("/cache/smember/{key}")
    public Set smember(@PathVariable(value = "key") String key) {
        log.info("【缓存模块】 smember，获取key ={} 的数据", key);
        Set<Object> values = redisClient.sMembers(key);
        log.info("【缓存模块】 smember，获取key ={} 的数据 value = {}", key, values);
        return values;
    }

    @PostMapping("/cache/pipeline/string")
    public void pipelineString(@RequestBody Map<String, String> map) {
        log.info("【缓存模块】 pipelineString，获取到存储的数据，map的长度 ={}的数据", map.size());
        redisClient.pipelined(operations -> {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                operations.opsForValue().set(entry.getKey(), entry.getValue());
            }
        });
    }

    @PostMapping(value = "/cache/saddstr/{key}")
    public void saddStr(@PathVariable(value = "key") String key, @RequestBody String... value) {
        log.info("【缓存模块】 saddStr方法，存储key = {}，存储value = {}", key, value);
        redisClient.sAdd(key, value);
    }

    @PostMapping(value = "/cache/sinterstr/{key}/{sinterKey}")
    public Set<Object> sinterStr(@PathVariable(value = "key") String key, @PathVariable String sinterKey, @RequestBody String... value) {
        log.info("【缓存模块】 sinterStr的交集方法，存储key = {}，sinterKey = {}，存储value = {}", key, sinterKey, value);
        //1、 存储数据到set集合
        redisClient.sAdd(key, value);
        //2、 需要将key和sinterKey做交集操作，并拿到返回的set（RedisClient没提供，自己写的~~）
        Set<Object> result = redisClient.sIntersect(key, sinterKey);
        //3、 将key删除
        redisClient.delete(key);
        //4、 返回交集结果
        return result;
    }

    @PostMapping(value = "/cache/zadd/{key}/{score}/{member}")
    public Boolean zadd(@PathVariable(value = "key") String key,
                        @PathVariable(value = "score") Long score,
                        @PathVariable(value = "member") Object member) {
        log.info("【缓存模块】 zaddLong方法，存储key = {}，存储score = {}，存储value = {}", key, score, member);
        Boolean result = redisClient.zAdd(key, member, score);
        return result;
    }

    @DeleteMapping(value = "/cache/zremove/{key}/{member}")
    public void zRemove(@PathVariable(value = "key") String key, @PathVariable(value = "member") String member) {
        log.info("【缓存模块】 zRemove方法，存储key = {}，存储score = {}", key, member);
        redisClient.zRemove(key, member, member);
    }

    @GetMapping(value = "/cache/zrangebyscorecount/{key}/{start}/{end}")
    public int zRangeByScoreCount(@PathVariable(value = "key") String key, @PathVariable(value = "start") Double start, @PathVariable(value = "end") Double end) {
        log.info("【缓存模块】 zRangeByScoreCount方法，查询key = {},start = {},end = {}", key, start, end);
        Set<ZSetOperations.TypedTuple<Object>> values = redisTemplate.opsForZSet().rangeByScoreWithScores(key, start, end);
        if (values != null) {
            return values.size();
        }
        return 0;
    }

}
