package com.order.dining.service.impl;

import com.order.dining.dto.OrderDTO;
import com.order.dining.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
        //todo 测试号增加模版，填充模版Id
        wxMpTemplateMessage.setTemplateId("");
        wxMpTemplateMessage.setToUser(orderDTO.getBuyerOpenid());
        wxMpTemplateMessage.setData(null);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模版消息推送】消息发送失败,", e);
            e.printStackTrace();
        }
    }
}
