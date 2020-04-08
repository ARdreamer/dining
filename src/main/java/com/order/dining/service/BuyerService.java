package com.order.dining.service;

import com.order.dining.beans.dto.OrderDTO;

/**
 * @Author: baojx
 * @Date: 2020/2/15 15:21
 * @Desc: 买家服务
 */
public interface BuyerService {

    public OrderDTO selectOneOrder(String openId,String orderId);

    public OrderDTO cancelOrder(String openId,String orderId);
}
