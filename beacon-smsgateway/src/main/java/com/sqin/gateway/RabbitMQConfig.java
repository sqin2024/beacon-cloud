package com.sqin.gateway;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq配置类，可以在指定的Listener上注解使用
 * @Author Qin
 * @Date 2025/5/9 14:30
 * @Description
 **/
//@Configuration
public class RabbitMQConfig {

//    @Bean
    public SimpleRabbitListenerContainerFactory gatewayContainerFactory(ConnectionFactory connectionFactory, SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConcurrentConsumers(10);
        containerFactory.setPrefetchCount(50);
        containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        configurer.configure(containerFactory, connectionFactory);
        return containerFactory;
    }

}
