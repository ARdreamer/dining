package com.order.dining.service.impl;

import com.alibaba.fastjson.JSON;
import com.order.dining.common.PageRequest;
import com.order.dining.common.PageResult;
import com.order.dining.dao.domain.OrderDetail;
import com.order.dining.dto.OrderDTO;
import com.order.dining.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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

    @Resource
    private PayOrderService payOrderService;

    @Test
    public void create() {
        for (int i = 0; i < 50; i++) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setBuyerName("baojx:" + i);
            orderDTO.setBuyerOpenid("1234");
            orderDTO.setBuyerPhone("11111111");
            orderDTO.setBuyerAddress("1111");

            List<OrderDetail> orderDetailList = new ArrayList<>();

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProductId(i % 4 + 1 + "");
            orderDetail.setProductNum(2);
            orderDetailList.add(orderDetail);
            orderDTO.setOrderDetailList(orderDetailList);

            OrderDTO orderDTO1 = payOrderService.create(orderDTO);
            log.info("【创建】：{}", JSON.toJSONString(orderDTO1, true));

        }

    }

    @Test
    public void selectOne() {
    }

    @Test
    public void selectByBuyerOpenId() {
        PageRequest pageRequest = new PageRequest(0, 2);
        PageResult pageResult = payOrderService.selectByBuyerOpenId(pageRequest, "1234");
        System.out.println(pageResult);
    }

    @Test
    public void cancel() {
    }

    @Test
    public void close() {
        OrderDTO orderDTO = payOrderService.selectOne("1581919950012191423");
        payOrderService.close(orderDTO);
    }

    @Test
    public void payOrder() {
    }
}
