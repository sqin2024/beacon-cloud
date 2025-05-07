package com.sqin.strategy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Qin
 * @Date 2025/5/7 12:28
 * @Description
 **/
@Configuration
@Slf4j
public class RabbitTemplateConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        // 1、构建RabbitTemplate对象
        RabbitTemplate rabbitTemplate = new RabbitTemplate();

        // 2、设置connectionFactory
        rabbitTemplate.setConnectionFactory(connectionFactory);

        // 3、配置confirm机制
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                // ack为false，代表消息没有发送到exchange
                if (!ack) {
                    log.error("【策略模块-发送消息】 消息没有发送到交换机，correlationData = {}，cause = {}", correlationData, cause);
                }
            }
        });

        // 4、配置return机制
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.error("【策略模块-发送消息】 消息没有路由到指定的Queue。 message = {},exchange = {},routingKey = {}",
                        new String(message.getBody()), exchange, routingKey);
            }
        });

        return rabbitTemplate;
    }


}
