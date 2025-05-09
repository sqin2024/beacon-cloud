package com.sqin.push.mq;

import com.rabbitmq.client.Channel;
import com.sqin.common.constant.RabbitMQConstants;
import com.sqin.common.model.StandardReport;
import com.sqin.common.utils.JsonUtil;
import com.sqin.push.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    private int[] delayTime = {0, 15000, 30000, 60000, 300000};

    private static final String SUCCESS = "SUCCESS";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 监听策略模块写进MQ中的消息
     *
     * @param report
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitListener(queues = RabbitMQConstants.SMS_PUSH_REPORT)
    public void consume(StandardReport report, Channel channel, Message message) throws IOException {
        // 1、获取客户的回调地址
        String callbackUrl = report.getCallbackUrl();
        if (StringUtils.isEmpty(callbackUrl)) {
            log.info("【推送模块-推送状态报告】 客户方没有设置回调的地址信息！callbackUrl = {} ", callbackUrl);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }

        //2、发送状态报告
        boolean reported = pushReport(report);
        isResend(report, reported);

        //4、直接手动ack
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 监听延迟队列的消息
     * @param report
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitListener(queues = RabbitMQConfig.DELAYED_QUEUE)
    public void delayedConsume(StandardReport report, Channel channel, Message message) throws IOException {
        //2、发送状态报告
        boolean flag = pushReport(report);
        isResend(report, flag);

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
            log.info("【推送模块-推送状态报告】 第{}次推送状态报告开始！report = {}", report.getResendCount(), report);
            String result = restTemplate.postForObject("http://" + report.getCallbackUrl(), new HttpEntity<>(body, httpHeaders), String.class);
            flag = SUCCESS.equals(result);
        } catch (RestClientException e) {
        }
        //3、得到响应后，确认是否为SUCCESS
        return flag;
    }

    /**
     * 判断状态报告是否推送成功，失败的话需要发送重试消息
     *
     * @param report
     * @param flag
     */
    private void isResend(StandardReport report, boolean flag) {
        if (!flag) {
            log.info("【推送模块-推送状态报告】 第{}次推送状态报告失败！report = {}", report.getResendCount(), report);

            if(report.getResendCount() == 4) {
                log.info("【推送模块-推送状态报告】 尝试次数已满，推送失败。");
                return;
            }

            report.setResendCount(report.getResendCount() + 1);
            rabbitTemplate.convertAndSend(RabbitMQConfig.DELAYED_EXCHANGE, "", report, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    // 设置延迟时间
                    message.getMessageProperties().setDelay(delayTime[report.getResendCount()]);
                    return message;
                }
            });
        } else {
            log.info("【推送模块-推送状态报告】 第{}次推送状态报告成功！report = {}", report.getResendCount() + 1, report);
        }
    }

}
