package com.order.dining.converter;

import com.alibaba.fastjson.JSON;
import com.order.dining.dao.domain.OrderDetail;
import com.order.dining.dto.CartDTO;
import com.order.dining.dto.OrderDTO;
import com.order.dining.enums.EResultError;
import com.order.dining.exception.DiningException;
import com.order.dining.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: baojx
 * @Date: 2020/2/15 14:16
 * @Desc: OrderForm转换为OrderDTO
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetailList;
        try {
            List<CartDTO> cartDTOList = JSON.parseArray(orderForm.getItems(), CartDTO.class);
            orderDetailList = cartDTOList.stream()
                    .map(e -> new OrderDetail(e.getProductId(), e.getProductQuantity()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("【格式转换】data={}", JSON.toJSONString(orderForm.getItems(), true));
            throw new DiningException(EResultError.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
