package com.sqin.search.service;

import java.io.IOException;

/**
 * @Author Qin
 * @Date 2025/5/8 23:14
 * @Description
 **/
public interface ElasticSearchService {

    void index(String index, String id, String json) throws IOException;

}
