package com.sqin.tools.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqin.tools.client.CacheClient;
import com.sqin.tools.entity.Channel;
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
 * @Date 2025/5/8 16:51
 * @Description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ChannelMapperTest {

    @Autowired
    private CacheClient cacheClient;

    @Autowired
    private ChannelMapper channelMapper;

    @Test
    public void findAll() throws JsonProcessingException {

        List<Channel> channelList = channelMapper.findAll();

        for (Channel channel : channelList) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map map = objectMapper.readValue(objectMapper.writeValueAsString(channel), Map.class);
            cacheClient.hmset("channel:" + channel.getId(), map);
        }

    }
}