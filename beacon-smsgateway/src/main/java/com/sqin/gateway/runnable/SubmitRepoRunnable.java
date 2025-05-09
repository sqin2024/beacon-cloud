package com.sqin.gateway.runnable;

import com.sqin.common.constant.RabbitMQConstants;
import com.sqin.common.constant.SmsConstants;
import com.sqin.common.model.StandardReport;
import com.sqin.common.model.StandardSubmit;
import com.sqin.common.utils.CMPP2ResultUtil;
import com.sqin.common.utils.CMPPDeliverMapUtil;
import com.sqin.common.utils.CMPPSubmitRepoMapUtil;
import com.sqin.gateway.netty4.entity.CmppSubmitResp;
import com.sqin.gateway.util.SpringUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;

/**
 * @Author Qin
 * @Date 2025/5/9 23:41
 * @Description
 **/
public class SubmitRepoRunnable implements Runnable{

    private RabbitTemplate rabbitTemplate = SpringUtil.getBeanByClass(RabbitTemplate.class);

    private CmppSubmitResp submitResp;

    private final int OK = 0;

    public SubmitRepoRunnable(CmppSubmitResp submitResp) {
        this.submitResp = submitResp;
    }

    @Override
    public void run() {
        StandardReport report = null;

        StandardSubmit submit = CMPPSubmitRepoMapUtil.remove(submitResp.getSequenceId());
        int result = submitResp.getResult();
        if (result != OK) {
            // 到这，说明运营商的提交应答中回馈的失败的情况
            String resultMessage = CMPP2ResultUtil.getResultMessage(result);
            submit.setReportState(SmsConstants.REPORT_FAIL);
            submit.setErrorMsg(resultMessage);
        } else {
            // 如果没进到if中，说明运营商已经正常的接收了发送短信的任务，这边完成3操作
            //3、将submit封装为Report，临时存储，以便运营商返回状态码时，可以再次获取到信息
            // 这里没有对其他信息做封装
            report = new StandardReport();
            BeanUtils.copyProperties(submit, report);
            CMPPDeliverMapUtil.put(submitResp.getMsgId() + "",report);
        }
        //4、将封装好的submit直接扔RabbitMQ中，让搜索模块记录信息
        rabbitTemplate.convertAndSend(RabbitMQConstants.SMS_WRITE_LOG,submit);

    }
}
