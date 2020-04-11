package com.order.dining.service.impl;

import com.alibaba.fastjson.JSON;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.order.dining.common.Constants;
import com.order.dining.beans.dto.OrderDTO;
import com.order.dining.common.enums.EResultError;
import com.order.dining.exception.DiningException;
import com.order.dining.service.PayOrderService;
import com.order.dining.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @Author: baojx
 * @Date: 2020/2/17 12:34
 * @Desc: 支付
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Resource
    private BestPayServiceImpl bestPayService;

    @Resource
    private PayOrderService payOrderService;

    @Override
    public PayResponse pay(OrderDTO orderDTO) {
        //1. 创建支付订单请求
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOpenid(orderDTO.getOrderId());
        payRequest.setOrderName(Constants.Pay.ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信：创建支付】微信支付请求req={}", JSON.toJSONString(payRequest, true));

        //2. 发起支付获取响应结果
        //因为不能申请商户，所以这里不能真实调用微信之所，我先自行mock，如果以后可以申请商户号了，就能直接解除注释
//        PayResponse payResponse = bestPayService.pay(payRequest);
        PayResponse payResponse = new PayResponse();
        payResponse.setOrderId(orderDTO.getOrderId());
        payResponse.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        log.info("【微信：创建支付】微信支付响应结果rsp={}", JSON.toJSONString(payResponse, true));
        return payResponse;
    }

    @Override
//    public PayResponse notify(String data) {
    public PayResponse notify(String orderId, Double orderAmount) {
        //TODO 有商户号了解除
        //PayResponse payResponse = bestPayService.asyncNotify(data);
        //log.info("【微信：异步回调】payRsp={}", JSON.toJSONString(payResponse, true));
        try {
            //模拟数据的延时
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PayResponse payResponse = new PayResponse();
        //修改订单状态
        OrderDTO orderDTO = payOrderService.selectOne(orderId);
        if (null == orderDTO) {
            log.error("【微信：异步回调】订单不存在,orderId={}", orderId);
            throw new DiningException(EResultError.ORDER_NOT_EXIST);
        }
        //double精度问题
        double abs = Math.abs(orderDTO.getOrderAmount().doubleValue() - orderAmount);
        if (abs >= Constants.Pay.DOUBLE_VALUE) {
            log.error("【微信：异步回调】订单金额不一致,orderId=[{}],微信金额=[{}],订单金额=[{}]", orderId, orderAmount, orderDTO.getOrderAmount());
            throw new DiningException(EResultError.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
        payOrderService.payOrder(orderDTO);

        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信：退款】微信退款请求req={}", JSON.toJSONString(refundRequest, true));
        //TODO 有商户号了解除
//        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        RefundResponse refundResponse = new RefundResponse();
        log.info("【微信：退款】微信退款响应结果rsp={}", JSON.toJSONString(refundResponse, true));

        return refundResponse;
    }

}
