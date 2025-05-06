package com.sqin.tools.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqin.tools.client.CacheClient;
import com.sqin.tools.entity.ClientSign;
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
 * @Date 2025/5/6 20:37
 * @Description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientSignMapperTest {

    @Autowired
    private ClientSignMapper clientSignMapper;

    @Autowired
    private CacheClient cacheClient;

    @Test
    public void findByClientId() {

        List<ClientSign> clientSignList = clientSignMapper.findByClientId(1l);
        for (ClientSign clientSign : clientSignList) {
            System.out.println(clientSign.toString());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map> collect = clientSignList.stream().map(clientSign -> {
            try {
                return objectMapper.readValue(objectMapper.writeValueAsString(clientSign), Map.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());

        cacheClient.sadd("client_sign:1", collect.toArray(new Map[]{}));
    }
}