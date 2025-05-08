package com.sqin.search.mq;

import com.rabbitmq.client.Channel;
import com.sqin.common.constant.RabbitMQConstants;
import com.sqin.common.model.StandardSubmit;
import com.sqin.common.utils.JsonUtil;
import com.sqin.search.service.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;


/**
 * @Author Qin
 * @Date 2025/5/8 23:05
 * @Description
 **/
@Component
@Slf4j
public class SmsWriteLogListener {

    @Autowired
    private ElasticSearchService elasticSearchService;

    private final static String INDEX = "sms_submit_log_";

    @RabbitListener(queues = RabbitMQConstants.SMS_WRITE_LOG)
    public void consume(StandardSubmit submit, Channel channel, Message message) throws IOException {
        log.info("接收到了存储日志的信息submit = {}", submit);

        elasticSearchService.index(INDEX + getYear(), submit.getSequenceId().toString(), JsonUtil.obj2JSON(submit));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    public String getYear() {
        return LocalDateTime.now().getYear() + "";
    }
}
