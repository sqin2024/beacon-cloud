package com.sqin.search.service.impl;

import com.sqin.search.service.ElasticSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @Author Qin
 * @Date 2025/5/8 23:27
 * @Description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticSearchServiceImplTest {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Test
    public void index() throws IOException {
        elasticSearchService.index("sms_submit_log_2025", "3", "{\"clientId\":3}");
    }
}