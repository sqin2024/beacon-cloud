package com.sqin.tools.mapper;

import com.sqin.tools.client.CacheClient;
import com.sqin.tools.entity.MobileArea;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Qin
 * @Date 2025/5/7 15:37
 * @Description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class MobileAreaMapperTest {
    @Autowired
    private MobileAreaMapper mapper;

    @Autowired
    CacheClient cacheClient;

    @Test
    public void findAll() {
        List<MobileArea> list = mapper.findAll();
        Map map = new HashMap(list.size());
        for (MobileArea mobileArea : list) {
            map.put("phase:" + mobileArea.getMobileNumber(), mobileArea.getMobileArea() + "," + mobileArea.getMobileType());
        }
        cacheClient.pipelineString(map);
    }

}