package com.order.dining.controller.buyer;

import com.lly835.bestpay.model.PayResponse;
import com.order.dining.dto.OrderDTO;
import com.order.dining.enums.EResultError;
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
public class PayController {

    @Resource
    private PayOrderService payOrderService;

    @Resource
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) {
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

        return new ModelAndView("pay/create", map);
    }

    @PostMapping("notify")
    public ModelAndView notify(@RequestBody String data) {
        payService.notify(data);

        return new ModelAndView("pay/success");
    }

}
