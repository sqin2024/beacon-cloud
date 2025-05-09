package com.sqin.gateway.mq;

import com.rabbitmq.client.Channel;
import com.sqin.common.model.StandardSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author Qin
 * @Date 2025/5/9 14:19
 * @Description
 **/
@Component
@Slf4j
public class SmsGatewayListener {

    //    @RabbitListener(queues = "${gateway.sendtopic}", containerFactory = "gatewayContainerFactory")
    @RabbitListener(queues = "${gateway.sendtopic}")
    public void consume(String submit, Channel channel, Message message) throws IOException {
        log.info("【短信网关模块】 接收到消息 submit = {}", submit);
        // =====================完成运营商交互，发送一次请求，接收两次响应==========================
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
