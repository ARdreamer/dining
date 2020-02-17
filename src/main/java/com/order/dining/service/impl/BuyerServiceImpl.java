package com.order.dining.service.impl;

import com.alibaba.fastjson.JSON;
import com.order.dining.dto.OrderDTO;
import com.order.dining.enums.EResultError;
import com.order.dining.exception.DiningException;
import com.order.dining.service.BuyerService;
import com.order.dining.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: baojx
 * @Date: 2020/2/15 15:24
 * @Desc: 买家服务，需要自己判断是否为本人操作，所以另外从PayOrderService中抽离出相关操作
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Resource
    private PayOrderService payOrderService;

    @Override
    public OrderDTO selectOneOrder(String openId, String orderId) {
        return check(openId, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openId, String orderId) {
        OrderDTO orderDTO = check(openId, orderId);
        if (null == orderDTO) {
            log.error("【取消订单】查询不到指定订单，orderId={}", orderId);
            throw new DiningException(EResultError.ORDER_NOT_EXIST);
        }

        return payOrderService.cancel(orderDTO);
    }

    private OrderDTO check(String openId, String orderId) {
        OrderDTO orderDTO = payOrderService.selectOne(orderId);
        if (null == orderDTO) {
            return null;
        }
        if (!StringUtils.equals(openId, orderDTO.getBuyerOpenid())) {
            log.error("【查询订单】订单openId不一致。openId={}, orderDTO={}", openId, JSON.toJSONString(orderDTO));
            throw new DiningException(EResultError.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
