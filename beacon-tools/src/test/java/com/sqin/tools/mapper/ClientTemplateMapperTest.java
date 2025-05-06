package com.sqin.tools.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqin.tools.client.CacheClient;
import com.sqin.tools.entity.ClientTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Qin
 * @Date 2025/5/6 21:24
 * @Description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientTemplateMapperTest {

    @Autowired
    private ClientTemplateMapper clientTemplateMapper;

    @Autowired
    private CacheClient cacheClient;

    @Test
    public void findBySignId() {
        List<ClientTemplate> ct15 = clientTemplateMapper.findBySignId(15l);
        List<ClientTemplate> ct24 = clientTemplateMapper.findBySignId(15l);
        System.out.println(ct15);
        System.out.println(ct24);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map> values = ct15.stream().map(clientTemplate -> {
            try {
                return objectMapper.readValue(objectMapper.writeValueAsString(clientTemplate), Map.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
        cacheClient.sadd("client_template:15", values.toArray(new Map[]{}));
    }
}