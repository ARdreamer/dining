package com.order.dining.service.impl;

import com.github.pagehelper.*;
import com.order.dining.common.*;
import com.order.dining.dao.domain.*;
import com.order.dining.dao.mappers.*;
import com.order.dining.dto.CartDTO;
import com.order.dining.dto.OrderDTO;
import com.order.dining.enums.*;
import com.order.dining.exception.DiningException;
import com.order.dining.service.*;
import com.order.dining.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: baojx
 * @Date: 2020/2/12 13:54
 */
@Service
@Slf4j
public class PayOrderServiceImpl implements PayOrderService {

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //1. 查询商品（库存，价格） ps:价格不能取前端传来的值，防止被篡改
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.selectOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new DiningException(EResultError.PRODUCT_NOT_EXIST);
            }

            //计算总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductNum()))
                    .add(orderAmount);

            //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setCreateTime(new Date());
            orderDetail.setUpdateTime(new Date());
            orderDetailMapper.insert(orderDetail);
            log.info("【订单详情插入】:{}", orderDetail);
        }

        //2. 写数据库（order和orderDetail）
        PayOrder payOrder = new PayOrder();
        orderDTO.setOrderId(orderId);
        orderDTO.setCreateTime(new Date());
        orderDTO.setUpdateTime(new Date());
        BeanUtils.copyProperties(orderDTO, payOrder);
        payOrder.setOrderAmount(orderAmount);
        payOrder.setOrderStatus(EOrderStatus.NEW.getCode().byteValue());
        payOrder.setPayStatus(EPayOrderStatus.NO_PAY.getCode().byteValue());
        log.error("【插入订单】:{}", payOrder);
        payOrderMapper.insert(payOrder);

        //3. 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductNum()))
                .collect(Collectors.toList());
        productService.decrStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO selectOne(String orderId) {
        PayOrder payOrder = payOrderMapper.selectByPrimaryKey(orderId);
        if (payOrder == null) {
            throw new DiningException(EResultError.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailMapper.selectByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new DiningException(EResultError.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(payOrder, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        log.info("【查询订单】:{}", orderDTO);
        return orderDTO;
    }

    @Override
    public PageResult selectByBuyerOpenId(PageRequest pageRequest, String openId) {
        return PageUtil.getPageResult(pageRequest, getPageInfo(pageRequest, openId));
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO payOrder(OrderDTO orderDTO) {
        return null;
    }

    /**
     * 调用分页插件完成分页
     *
     * @param pageRequest 分页请求
     * @param openId      用户openId
     * @return 分页信息
     */
    private PageInfo<PayOrder> getPageInfo(PageRequest pageRequest, String openId) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<PayOrder> payOrderList = payOrderMapper.selectByBuyerOpenId(openId);
        log.error("【分页查询】：{}", payOrderList);
        return new PageInfo<>(payOrderList);
    }
}
