package com.sqin.tools.mapper;

import com.sqin.tools.client.CacheClient;
import com.sqin.tools.entity.MobileBlack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author Qin
 * @Date 2025/5/8 11:05
 * @Description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class MobileBlackMapperTest {

    @Autowired
    private MobileBlackMapper mapper;

    @Autowired
    private CacheClient cacheClient;

    @Test
    public void findAll() {
        List<MobileBlack> mobileBlackList = mapper.findAll();

        for (MobileBlack mobileBlack : mobileBlackList) {
            if (mobileBlack.getClientId() == 0) {
                // 平台级别的黑名单   black:手机号   作为key
                cacheClient.set("black:" + mobileBlack.getBlackNumber(), "1");
            } else {
                // 客户级别的黑名单   black:clientId:手机号
                cacheClient.set("black:" + mobileBlack.getClientId() + ":" + mobileBlack.getBlackNumber(), "1");
            }
        }
    }
}