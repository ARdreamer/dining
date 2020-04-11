package com.order.dining.controller.buyer;

import com.lly835.bestpay.model.PayResponse;
import com.order.dining.beans.dto.OrderDTO;
import com.order.dining.common.enums.EResultError;
import com.order.dining.exception.DiningException;
import com.order.dining.service.PayOrderService;
import com.order.dining.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: baojx
 * @Date: 2020/2/17 12:29
 * @Desc: 支付
 */
@Controller
@RequestMapping("/pay")
@Slf4j
public class BuyerPayController {

    @Resource
    private PayOrderService payOrderService;

    @Resource
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) {
//        log.info("=================================create");
        //1. 查询订单
        OrderDTO orderDTO = payOrderService.selectOne(orderId);
        if (null == orderDTO) {
            log.error("【创建支付】订单id不存在，orderId={}", orderId);
            throw new DiningException(EResultError.ORDER_NOT_EXIST);
        }
        //2. 发起支付
        PayResponse payResponse = payService.pay(orderDTO);
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);

        //TODO 为了模拟支付，所以把回调的逻辑放在了这了，因为微信默认实时是不准确的，所以我们应该依靠回调，而不是自己去修改订单状态
        payService.notify(orderId, payResponse.getOrderAmount());

        //TODO 为了完整流程，所以在create中重定向一次
        return new ModelAndView("pay/create", map);
    }

    @GetMapping("notify")
//    public ModelAndView notify(@RequestBody String data) {
    public ModelAndView notify(@RequestParam("orderId") String orderId,
                               @RequestParam("orderAmount") Double orderAmount) {
        payService.notify(orderId, orderAmount);

        return new ModelAndView("pay/success");
    }

}
