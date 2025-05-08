package com.sqin.search.service.impl;

import com.sqin.common.enums.ExceptionEnums;
import com.sqin.common.exception.SearchException;
import com.sqin.search.service.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author Qin
 * @Date 2025/5/8 23:15
 * @Description
 **/
@Service
@Slf4j
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private final String CREATED = "created";

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void index(String index, String id, String json) throws IOException {
        IndexRequest request = new IndexRequest();
        request.index(index);
        request.id(id);
        request.source(json, XContentType.JSON);

        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        String result = indexResponse.getResult().getLowercase();

        if (!CREATED.equals(result)) {
            log.error("【搜索模块 - 写入数据失败】 index = {}, id = {}, json = {}, result = {}", index, id, json, result);
            throw new SearchException(ExceptionEnums.INDEX_ERROR);
        }

        log.info("【搜索模块 - 写入数据成功】 index = {}, id = {}, json = {}, result = {}", index, id, json, result);
    }

}
