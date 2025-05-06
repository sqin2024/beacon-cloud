package com.sqin.tools.mapper;

import com.sqin.tools.client.CacheClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void findByClientId() {
        Long balance = clientBalanceMapper.findByClientId(1l);
        cacheClient.set("client_balance:1", balance);
    }
}