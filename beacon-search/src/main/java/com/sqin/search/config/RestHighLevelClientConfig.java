package com.sqin.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author Qin
 * @Date 2025/5/8 22:56
 * @Description
 **/
@Configuration
public class RestHighLevelClientConfig {

    @Value("#{'${elasticsearch.hostAndPorts}'.split(',')}")
    private List<String> hostAndPorts;

    /**
     * 构建连接es的client对象
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        HttpHost[] httpHosts = new HttpHost[hostAndPorts.size()];
        for (int i = 0; i < hostAndPorts.size(); i++) {
            String[] hostAndPort = hostAndPorts.get(i).split(":");
            httpHosts[i] = new HttpHost(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
        }

        RestClientBuilder restClientBuilder = RestClient.builder(httpHosts);
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);
        return restHighLevelClient;
    }

}
