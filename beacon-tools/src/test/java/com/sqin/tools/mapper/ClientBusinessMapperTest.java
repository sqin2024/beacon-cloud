package com.sqin.tools.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqin.tools.client.CacheClient;
import com.sqin.tools.entity.ClientBusiness;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;


/**
 * @Author Qin
 * @Date 2025/5/6 19:58
 * @Description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientBusinessMapperTest {

    @Autowired
    private ClientBusinessMapper clientBusinessMapper;

    @Autowired
    private CacheClient cacheClient;

    @Test
    public void findById() throws JsonProcessingException {
        ClientBusiness cb = clientBusinessMapper.findById(1L);
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.readValue(objectMapper.writeValueAsString(cb), Map.class);
        cacheClient.hmset("client_business:" + cb.getApikey(),map);
    }
}