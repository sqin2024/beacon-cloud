package com.sqin.tools.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqin.tools.client.CacheClient;
import com.sqin.tools.entity.Channel;
import com.sqin.tools.entity.ClientChannel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Author Qin
 * @Date 2025/5/8 16:59
 * @Description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientChannelMapperTest {
    @Autowired
    private CacheClient cacheClient;

    @Autowired
    private ClientChannelMapper clientChannelMapper;

    @Test
    public void findAll() throws JsonProcessingException {
        List<ClientChannel> clientChannelList = clientChannelMapper.findAll();

        for (ClientChannel clientChannel : clientChannelList) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map map = objectMapper.readValue(objectMapper.writeValueAsString(clientChannel), Map.class);
            cacheClient.sadd("client_channel:" + clientChannel.getClientId(), map);
        }
    }
}