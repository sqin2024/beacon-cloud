package com.sqin.common.constant;

/**
 * @Author Qin
 * @Date 2025/5/7 12:22
 * @Description
 **/
public class RabbitMQConstants {

    /**
     * 接口模块 发送短信 给策略模块的队列
     */
    public static final String SMS_PRE_SEND = "sms_pre_send_topic";

    /**
     * 第三方查到手机号消息后，往redis里写数据的队列，目前没有接收端
     */
    public static final String MOBILE_AREA_OPERATOR = "mobile_area_operator_topic";

    /**
     * 写日志的队列
     */
    public static final String SMS_WRITE_LOG = "sms_write_log_topic";

}
