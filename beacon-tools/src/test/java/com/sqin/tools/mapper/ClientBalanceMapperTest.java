package com.sqin.tools.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqin.tools.client.CacheClient;
import com.sqin.tools.entity.ClientBalance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @Author Qin
 * @Date 2025/5/6 21:41
 * @Description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientBalanceMapperTest {

    @Autowired
    private CacheClient cacheClient;

    @Autowired
    private ClientBalanceMapper clientBalanceMapper;

    @Test
    public void findByClientId() throws JsonProcessingException {
        ClientBalance balance = clientBalanceMapper.findByClientId(1l);
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.readValue(objectMapper.writeValueAsString(balance), Map.class);
        cacheClient.hmset("client_balance:1", map);
    }
}