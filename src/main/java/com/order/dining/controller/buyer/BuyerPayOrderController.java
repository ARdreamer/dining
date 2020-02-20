package com.order.dining.controller.buyer;

import com.alibaba.fastjson.JSON;
import com.order.dining.common.PageRequest;
import com.order.dining.common.PageResult;
import com.order.dining.common.Result;
import com.order.dining.converter.OrderForm2OrderDTOConverter;
import com.order.dining.dto.OrderDTO;
import com.order.dining.enums.EResultError;
import com.order.dining.exception.DiningException;
import com.order.dining.form.OrderForm;
import com.order.dining.service.BuyerService;
import com.order.dining.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: baojx
 * @Date: 2020/2/15 14:03
 * @Desc: 订单controller
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerPayOrderController {

    @Resource
    private PayOrderService payOrderService;
    @Resource
    private BuyerService buyerService;

    @PostMapping("/create")
    public Result<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数错误，orderForm={}", JSON.toJSONString(orderForm, true));
            throw new DiningException(EResultError.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车为空，orderDTO={}", JSON.toJSONString(orderDTO, true));
            throw new DiningException(EResultError.CART_EMPTY);
        }

        OrderDTO result = payOrderService.create(orderDTO);

        Map<String, String> map = new HashMap<>(4);
        map.put("orderId", result.getOrderId());

        return new Result<>(map);
    }

    @GetMapping("/list")
    public Result<List<OrderDTO>> list(@RequestParam("openid") String openId,
                                       @RequestParam(value = "page", defaultValue = "0") Integer page,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isBlank(openId)) {
            log.error("【查询订单列表】openId为空");
            throw new DiningException(EResultError.PARAM_ERROR);
        }

        PageRequest pageRequest = new PageRequest(page, size);
        PageResult pageResult = payOrderService.selectByBuyerOpenId(pageRequest, openId);
        List<OrderDTO> orderDTOList = null;
        try {
            orderDTOList = (List<OrderDTO>) pageResult.getContent();
        } catch (Exception e) {
            log.error("【查询订单列表】格式转换失败");
        }
        return new Result<>(orderDTOList);
    }

    @GetMapping("/detail")
    public Result<OrderDTO> detail(@RequestParam("openid") String openId, @RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = buyerService.selectOneOrder(openId, orderId);
        return new Result<>(orderDTO);
    }

    @PostMapping("/cancel")
    public Result<Object> cancel(@RequestParam("openid") String openId, @RequestParam("orderId") String orderId) {
        buyerService.cancelOrder(openId, orderId);
        return new Result<>();
    }

}
