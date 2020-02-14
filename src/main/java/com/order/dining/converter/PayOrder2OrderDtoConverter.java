package com.order.dining.converter;

import com.order.dining.dao.domain.PayOrder;
import com.order.dining.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: baojx
 * @Date: 2020/2/14 15:39
 * @Desc: PayOrder2OrderDto
 */
public class PayOrder2OrderDtoConverter {

    public static OrderDTO convert(PayOrder payOrder) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(payOrder, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<PayOrder> payOrderList) {
        return payOrderList.stream()
                .map(PayOrder2OrderDtoConverter::convert)
                .collect(Collectors.toList());
    }
}
