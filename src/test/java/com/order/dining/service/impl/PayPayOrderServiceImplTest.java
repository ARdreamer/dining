package com.order.dining.service.impl;

import com.order.dining.dao.domain.OrderDetail;
import com.order.dining.dto.OrderDTO;
import com.order.dining.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: baojx
 * @Date: 2020/2/14 14:20
 * @Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayPayOrderServiceImplTest {

    @Autowired
    private PayOrderService payOrderService;

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("baojx");
        orderDTO.setBuyerOpenid("1234");
        orderDTO.setBuyerPhone("11111111");
        orderDTO.setBuyerAddress("1234");

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("1");
        orderDetail.setProductNum(2);
        orderDetailList.add(orderDetail);
        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("2");
        orderDetail1.setProductNum(3);
        orderDetailList.add(orderDetail1);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO orderDTO1 = payOrderService.create(orderDTO);
        log.info("【创建】：{}", orderDTO1);
    }

    @Test
    public void selectOne() {
    }

    @Test
    public void selectByBuyerOpenId() {
    }

    @Test
    public void cancel() {
    }

    @Test
    public void finish() {
    }

    @Test
    public void payOrder() {
    }
}
