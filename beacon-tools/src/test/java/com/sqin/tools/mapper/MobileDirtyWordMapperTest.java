package com.sqin.tools.mapper;

import com.sqin.tools.client.CacheClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author Qin
 * @Date 2025/5/7 16:44
 * @Description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class MobileDirtyWordMapperTest {

    @Autowired
    private MobileDirtyWordMapper mapper;

    @Autowired
    CacheClient cacheClient;


    @Test
    public void findDirtyWord() {
        List<String> dirtyWords = mapper.findDirtyWord();

        cacheClient.saddStr("dirty_word",dirtyWords.toArray(new String[]{}));
    }
}