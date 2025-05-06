package com.sqin.beaconapi.filter;

import com.sqin.common.model.StandardSubmit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author Qin
 * @Date 2025/5/5 18:45
 * @Description
 * @RefreshScope: Nacos中的内容变了，这里可以一起变
 **/
@Component
@RefreshScope
public class CheckFilterContext {

    // Spring的IOC会将对象全部放到Map集合中
    // 基于4.x中Spring提供的反省注解，基于Map只拿到需要的类型对象即可

    @Autowired
    private Map<String, CheckFilter> checkFiltersMap;

    // :后面是没有拿到值的情况下，值为:后面的
    @Value("${filters:apikey,ip,sign,template,mobile,fee}")
    private String filters;

    /**
     * 当前check方法用来管理校验链的顺序
     */
    public void check(StandardSubmit standardSubmit) {
        // 1，将获取到filters基于，做切分
        String[] filterArray = filters.split(",");
        // 2, 遍历数组即可
        for (String filter : filterArray) {
            CheckFilter checkFilter = checkFiltersMap.get(filter);
            checkFilter.check(standardSubmit);
        }
    }

}
