package com.order.dining.service.impl;

import com.order.dining.beans.dto.OrderDTO;
import com.order.dining.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/24 14:00
 * @Desc: 微信消息推送通知
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Resource
    private WxMpService wxMpService;

    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId("iHoP7bZODHafCEd01hEsSRNaXDNzXIYHcgyRGo4XMb4");
        wxMpTemplateMessage.setToUser(orderDTO.getBuyerOpenid());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        List<WxMpTemplateData> data = Arrays.asList(new WxMpTemplateData("first", "请查收您的订单哦!"),
                new WxMpTemplateData("keyword1", orderDTO.getOrderId()),
                new WxMpTemplateData("keyword2", orderDTO.getOrderAmount().toString()),
                new WxMpTemplateData("keyword3", orderDTO.getPayStatusEnum().getDesc()),
                new WxMpTemplateData("keyword4", simpleDateFormat.format(orderDTO.getUpdateTime())),
                new WxMpTemplateData("remark", "期待您的下次光临!"));
        wxMpTemplateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模版消息推送】消息发送失败,", e);
            e.printStackTrace();
        }
    }
}
