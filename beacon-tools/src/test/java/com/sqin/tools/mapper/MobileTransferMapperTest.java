package com.sqin.tools.mapper;

import com.sqin.tools.client.CacheClient;
import com.sqin.tools.entity.MobileTransfer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author Qin
 * @Date 2025/5/8 14:02
 * @Description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class MobileTransferMapperTest {

    @Autowired
    private MobileTransferMapper mapper;

    @Autowired
    private CacheClient cacheClient;

    @Test
    public void findAll() {
        List<MobileTransfer> mobileTransferList = mapper.findAll();

        for (MobileTransfer mobileTransfer : mobileTransferList) {
            cacheClient.set("transfer:" + mobileTransfer.getTransferNumber(), mobileTransfer.getNowIsp());
        }
    }
}