package com.order.dining.controller.seller;

import com.alibaba.fastjson.JSON;
import com.order.dining.beans.form.OrderSearchForm;
import com.order.dining.common.page.PageRequest;
import com.order.dining.common.page.PageResult;
import com.order.dining.beans.dto.OrderDTO;
import com.order.dining.common.enums.EResultError;
import com.order.dining.exception.DiningException;
import com.order.dining.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: baojx
 * @Date: 2020/2/19 11:40
 * @Desc: 卖家端
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerPayOrderController {

    @Resource
    private PayOrderService payOrderService;

    /**
     * 获取订单列表
     *
     * @param page 第几页
     * @param size 页面大小
     * @return 视图
     */
    @GetMapping("/list")
    public ModelAndView list(OrderSearchForm orderSearchForm,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page, size);
        PageResult pageResult = payOrderService.search(pageRequest, orderSearchForm);
        log.info(JSON.toJSONString(orderSearchForm, true));

        map.put("orderSearchForm", orderSearchForm);
        map.put("orderDTOPage", pageResult);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView(("order/list"), map);

    }

    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        OrderDTO orderDTO;
        try {
            orderDTO = payOrderService.selectOne(orderId);
            payOrderService.cancel(orderDTO);
        } catch (DiningException e) {
            log.error("【取消订单】异常，", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", EResultError.SUCCESS.getDesc());
        map.put("url", "/sell/seller/order/list");

        return new ModelAndView("common/success", map);
    }

    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        OrderDTO orderDTO;
        try {
            orderDTO = payOrderService.selectOne(orderId);
        } catch (DiningException e) {
            log.error("【订单详情】异常，", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("orderDTO", orderDTO);

        return new ModelAndView("order/detail", map);
    }

    @GetMapping("/close")
    public ModelAndView close(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        OrderDTO orderDTO;
        try {
            orderDTO = payOrderService.selectOne(orderId);
            payOrderService.close(orderDTO);
        } catch (DiningException e) {
            log.error("【关闭订单】异常，", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", EResultError.SUCCESS.getDesc());
        map.put("url", "/sell/seller/order/list");

        return new ModelAndView("common/success", map);
    }

}
