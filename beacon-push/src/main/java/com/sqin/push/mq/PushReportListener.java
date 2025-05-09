package com.sqin.push.mq;

import com.rabbitmq.client.Channel;
import com.sqin.common.constant.RabbitMQConstants;
import com.sqin.common.model.StandardReport;
import com.sqin.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @Author Qin
 * @Date 2025/5/9 10:09
 * @Description
 **/
@Component
@Slf4j
public class PushReportListener {

    private static final String SUCCESS = "SUCCESS";

    @Autowired
    private RestTemplate restTemplate;

    @RabbitListener(queues = RabbitMQConstants.SMS_PUSH_REPORT)
    public void consume(StandardReport report, Channel channel, Message message) throws IOException {
        // 1、获取客户的回调地址
        String callbackUrl = report.getCallbackUrl();
        if (!StringUtils.isEmpty(callbackUrl)) {
            log.info("【推送模块-推送状态报告】 客户方没有设置回调的地址信息！callbackUrl = {} ", callbackUrl);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }

        //2、发送状态报告
        boolean reported = pushReport(report);

        //3、如果发送失败，重试
        if (!reported) {
            log.info("【推送模块-推送状态报告】 第一次推送状态报告失败！report = {}", report);
            // TODO  重试~~
        } else {
            log.info("【推送模块-推送状态报告】 第一次推送状态报告成功！report = {}", report);
        }

        //4、直接手动ack
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    // 发送一次请求，给callbackUrl
    private boolean pushReport(StandardReport report) {
        // 声明返回结果，你默认为false
        boolean flag = false;

        //1、声明发送的参数
        String body = JsonUtil.obj2JSON(report);

        //2、声明RestTemplate的模板代码
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            log.info("【推送模块-推送状态报告】 第一次推送状态报告开始！report = {}", report);
            String result = restTemplate.postForObject("http://" + report.getCallbackUrl(), new HttpEntity<>(body, httpHeaders), String.class);
            flag = SUCCESS.equals(result);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        //3、得到响应后，确认是否为SUCCESS
        return flag;
    }

}
