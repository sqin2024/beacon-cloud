package com.sqin.push.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Qin
 * @Date 2025/5/9 10:44
 * @Description
 **/
@Configuration
public class RabbitMQConfig {

    public static final String DELAYED_EXCHANGE = "push_delayed_exchange";

    public static final String DELAYED_QUEUE = "push_delayed_queue";

    public static final String DELAYED_EXCHANGE_TYPE = "x-delayed-message";

    public static final String DELAYED_ROUTING_TYPE_KEY = "x-delayed-type";

    public static final String DELAYED_ROUTING_TYPE_FANOUT = "fanout";

    @Bean
    public Exchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put(DELAYED_ROUTING_TYPE_KEY, DELAYED_ROUTING_TYPE_FANOUT);

        Exchange delayedExchange = new CustomExchange(DELAYED_EXCHANGE, DELAYED_EXCHANGE_TYPE, false, false, args);
        return delayedExchange;
    }

    @Bean
    public Queue delayedQueue() {
        return QueueBuilder.durable(DELAYED_QUEUE).build();
    }

    @Bean
    public Binding deleyedBinding(Exchange delayedExchange, Queue delayedQueue) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with("").noargs();
    }

}
