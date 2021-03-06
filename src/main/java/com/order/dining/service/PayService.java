package com.order.dining.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.order.dining.beans.dto.OrderDTO;

/**
 * @Author: baojx
 * @Date: 2020/2/17 12:33
 * @Desc: 支付
 */
public interface PayService {

    public PayResponse pay(OrderDTO orderDTO);

    public PayResponse notify(String orderId, Double orderAmount);

    public RefundResponse refund(OrderDTO orderDTO);
}
