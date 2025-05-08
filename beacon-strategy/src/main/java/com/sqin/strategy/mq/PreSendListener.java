package com.sqin.strategy.mq;

import com.rabbitmq.client.Channel;
import com.sqin.common.constant.RabbitMQConstants;
import com.sqin.common.exception.StrategyException;
import com.sqin.common.model.StandardSubmit;
import com.sqin.strategy.filter.StrategyFilterContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author Qin
 * @Date 2025/5/7 14:41
 * @Description
 **/
@Component
@Slf4j
public class PreSendListener {

    /**
     * 整个策略模块的校验
     */
    @Autowired
    private StrategyFilterContext filterContext;

    @RabbitListener(queues = RabbitMQConstants.SMS_PRE_SEND)
    public void listen(StandardSubmit standardSubmit, Message message, Channel channel) throws IOException {
        log.info("【策略模块-接收消息】 接收到接口模块发送的消息 submit = {}", standardSubmit);
        long start = System.currentTimeMillis();
        System.out.println("校验开始时间 startTime: " + start);

        try {
            filterContext.strategy(standardSubmit);
            log.info("【策略模块-消费完毕】手动ack");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (StrategyException e) {
            log.info("【策略模块-消费失败】失败, msg = {}", e.getMessage());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("校验开始时间 endTime: " + end);
            System.out.println("校验费时：" + (start - end));
        }
    }

}
