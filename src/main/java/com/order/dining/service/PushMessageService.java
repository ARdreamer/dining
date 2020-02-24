package com.order.dining.service;

import com.order.dining.dto.OrderDTO;

/**
 * @Author: baojx
 * @Date: 2020/2/24 14:00
 * @Desc: 微信消息推送通知
 */
public interface PushMessageService {

    void orderStatus(OrderDTO orderDTO);
}
