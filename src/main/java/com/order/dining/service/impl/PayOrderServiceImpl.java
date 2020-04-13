package com.order.dining.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.*;
import com.order.dining.beans.form.SearchForm;
import com.order.dining.common.WebSocket;
import com.order.dining.common.page.PageRequest;
import com.order.dining.common.page.PageResult;
import com.order.dining.converter.PayOrder2OrderDtoConverter;
import com.order.dining.dao.domain.*;
import com.order.dining.dao.mappers.*;
import com.order.dining.beans.dto.CartDTO;
import com.order.dining.beans.dto.OrderDTO;
import com.order.dining.common.enums.*;
import com.order.dining.exception.DiningException;
import com.order.dining.service.*;
import com.order.dining.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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

    @Resource
    private PayOrderMapper payOrderMapper;

    @Resource
    private ProductService productService;

    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Resource
    private PayService payService;

    @Resource
    private PushMessageService pushMessageService;

    @Resource
    private WebSocket webSocket;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
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
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setCreateTime(new Date());
            orderDetail.setUpdateTime(new Date());
            orderDetailMapper.insert(orderDetail);
            log.info("【订单详情插入】:{}", JSON.toJSONString(orderDetail, true));

        }

        //2. 写数据库（order和orderDetail）
        PayOrder payOrder = new PayOrder();
        orderDTO.setOrderId(orderId);
        orderDTO.setCreateTime(new Date());
        orderDTO.setUpdateTime(new Date());
        orderDTO.setOrderAmount(orderAmount);
        orderDTO.setPayStatus(EPayStatus.NO_PAY.getCode().byteValue());
        orderDTO.setOrderStatus(EOrderStatus.NEW.getCode().byteValue());
        BeanUtils.copyProperties(orderDTO, payOrder);
        log.error("【插入订单】:{}", JSON.toJSONString(payOrder, true));
        payOrderMapper.insert(payOrder);

        //3. 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decrStock(cartDTOList);

        //发送WebSocket
        webSocket.sendMessage("有新的订单");

        ThreadPoolUtil.addTask(new Thread(() -> pushMessageService.orderStatus(orderDTO)));
        return orderDTO;
    }

    @Override
    public OrderDTO selectOne(String orderId) {
        //1. 查询数据库订单，判断是否为空
        PayOrder payOrder = payOrderMapper.selectByPrimaryKey(orderId);
        if (payOrder == null) {
            throw new DiningException(EResultError.ORDER_NOT_EXIST);
        }

        //2. 根据订单id查询订单详情
        List<OrderDetail> orderDetailList = orderDetailMapper.selectByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new DiningException(EResultError.ORDER_DETAIL_NOT_EXIST);
        }

        //3. 封装dto
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(payOrder, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        //4. 日志记录
        log.info("【查询订单】:{}", JSON.toJSONString(orderDTO, true));
        return orderDTO;
    }

    @Override
    public PageResult selectByBuyerOpenId(PageRequest pageRequest, String openId) {
        return PageUtil.getPageResult(getPageInfo(pageRequest, openId));
    }

    @Override
    public PageResult selectAll(PageRequest pageRequest) {
        return PageUtil.getPageResult(getPageInfo(pageRequest));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public OrderDTO cancel(OrderDTO orderDTO) {
        PayOrder payOrder = new PayOrder();
        //1. 判断订单状态
        if (!orderDTO.getOrderStatus().equals(EOrderStatus.NEW.getCode().byteValue())) {
            log.error("【取消订单】订单状态不正确，orderId={}, orderSts={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new DiningException(EResultError.ORDER_STATUS_ERROR);
        }

        //2. 修改订单状态
        orderDTO.setOrderStatus(EOrderStatus.CANCEL.getCode().byteValue());
        BeanUtils.copyProperties(orderDTO, payOrder);
        payOrder.setUpdateTime(new Date());
        int i = payOrderMapper.updateByPrimaryKeySelective(payOrder);
        if (i <= 0) {
            log.error("【取消订单】更新订单状态失败，order:{}", JSON.toJSONString(payOrder, true));
            throw new DiningException(EResultError.ORDER_UPDATE_FAIL);
        }

        //3. 修改库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情，orderDTO:{}", JSON.toJSONString(orderDTO, true));
            throw new DiningException(EResultError.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.incrStock(cartDTOList);

        //4. 若已支付，则退款
        if (orderDTO.getPayStatus().equals(EPayStatus.SUCCESS.getCode().byteValue())) {
            payService.refund(orderDTO);
        }

        return orderDTO;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public OrderDTO close(OrderDTO orderDTO) {
        //1. 判断订单状态
        if (!orderDTO.getOrderStatus().equals(EOrderStatus.NEW.getCode().byteValue())) {
            log.error("【关闭订单】订单状态不正确，orderId={}, orderSts={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new DiningException(EResultError.ORDER_STATUS_ERROR);
        }

        //2. 修改订单状态
        orderDTO.setOrderStatus(EOrderStatus.CLOSE.getCode().byteValue());
        PayOrder payOrder = new PayOrder();
        BeanUtils.copyProperties(orderDTO, payOrder);
        payOrder.setUpdateTime(new Date());
        int i = payOrderMapper.updateByPrimaryKeySelective(payOrder);
        if (i <= 0) {
            log.error("【关闭订单】更新订单状态失败，order:{}", JSON.toJSONString(payOrder, true));
            throw new DiningException(EResultError.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public OrderDTO payOrder(OrderDTO orderDTO) {
//        log.info("===============================payorder");
        //1. 判断订单状态
        if (!orderDTO.getOrderStatus().equals(EOrderStatus.NEW.getCode().byteValue())) {
            log.error("【支付订单】订单状态不正确，orderId={}, orderSts={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new DiningException(EResultError.ORDER_STATUS_ERROR);
        }

        //2. 判断支付状态
        if (!orderDTO.getPayStatus().equals(EPayStatus.NO_PAY.getCode().byteValue())) {
            log.error("【支付订单】订单支付状态不正确，orderId={}, paySts={}", orderDTO.getOrderId(), orderDTO.getPayStatus());
            throw new DiningException(EResultError.ORDER_PAY_STATUS_ERROR);
        }
        //3. 修改支付状态
        orderDTO.setUpdateTime(new Date());
        orderDTO.setPayStatus(EPayStatus.SUCCESS.getCode().byteValue());
        PayOrder payOrder = new PayOrder();
        BeanUtils.copyProperties(orderDTO, payOrder);
        int i = payOrderMapper.updateByPrimaryKeySelective(payOrder);
        if (i <= 0) {
            log.error("【支付订单】更新订单状态失败，order:{}", JSON.toJSONString(payOrder, true));
            throw new DiningException(EResultError.ORDER_UPDATE_FAIL);
        }

        ThreadPoolUtil.addTask(new Thread(() -> pushMessageService.orderStatus(orderDTO)));

        return orderDTO;
    }

    @Override
    public PageResult search(PageRequest pageRequest, SearchForm searchForm) {
        //1. 处理表单中时间格式，如果精确到天，则使用天，否则使用月份,防止bean对象被更改,后面还要用到
        SearchForm toSearch = new SearchForm();
        BeanUtils.copyProperties(searchForm, toSearch);
        doVerifyDate(toSearch);
        //2. 返回查询结果
        return PageUtil.getPageResult(getPageInfo(pageRequest, toSearch));
    }

    /**
     * 对前端传来的时间进行包装
     *
     * @param searchForm 查询表单
     */
    private void doVerifyDate(SearchForm searchForm) {
        String dateDay = searchForm.getDateDay();
        String dateMonth = searchForm.getDateMonth();
        if (StringUtils.isBlank(searchForm.getDateDay())) {
            searchForm.setDateDay(null);
        }
        if (StringUtils.isBlank(searchForm.getDateMonth())) {
            searchForm.setDateMonth(null);
        }
        if (StringUtils.isBlank(searchForm.getOrderId())) {
            searchForm.setOrderId(null);
        }
        if (StringUtils.isBlank(searchForm.getPhone())) {
            searchForm.setPhone(null);
        }
        if (StringUtils.isBlank(searchForm.getUsername())) {
            searchForm.setUsername(null);
        }
        if (StringUtils.isNotBlank(dateDay)) {
            searchForm.setDateDay(dateDay + " 00:00:00");
            searchForm.setDateDayEnd(dateDay + " 23:59:59");
            searchForm.setDateMonth(null);
        } else if (StringUtils.isNotBlank(dateMonth)) {
            searchForm.setDateMonth(dateMonth + "-01 00:00:00");
            searchForm.setDateMonthEnd(dateMonth + "-31 23:59:59");
        }
    }

    private PageInfo<OrderDTO> getPageInfo(PageRequest pageRequest) {
        return getPageInfo(pageRequest, null, null);
    }

    private PageInfo<OrderDTO> getPageInfo(PageRequest pageRequest, String openId) {
        return getPageInfo(pageRequest, openId, null);
    }

    private PageInfo<OrderDTO> getPageInfo(PageRequest pageRequest, SearchForm searchForm) {
        return getPageInfo(pageRequest, null, searchForm);
    }

    /**
     * 调用分页插件完成分页
     *
     * @param pageRequest 分页请求
     * @param openId      用户openId
     * @return 分页信息
     */
    //todo 重构 暂时写这样，有时间再写
    private PageInfo<OrderDTO> getPageInfo(PageRequest pageRequest, String openId, SearchForm searchForm) {
        //1. 获取分页请求
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        //2. 查询数据库
        List<PayOrder> payOrderList;
        if (searchForm != null) {
            payOrderList = payOrderMapper.searchByForm(searchForm);
        } else if (StringUtils.isBlank(openId)) {
            payOrderList = payOrderMapper.selectAll();
//        log.error("【分页查询】：{}", JSON.toJSONString(payOrderList, true));
        } else {
            payOrderList = payOrderMapper.selectByBuyerOpenId(openId);
        }
//        log.error("【分页查询】：{}", JSON.toJSONString(payOrderList, true));
        //3. 包装分页结果，并进行DTO转换
        PageInfo pageInfo = new PageInfo(payOrderList);
        List<OrderDTO> convert = PayOrder2OrderDtoConverter.convert(payOrderList);
//        log.error("================{}", convert);
        pageInfo.setList(convert);
        return pageInfo;
    }
}
