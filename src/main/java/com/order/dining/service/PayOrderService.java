package com.order.dining.service;

import com.order.dining.common.page.PageRequest;
import com.order.dining.common.page.PageResult;
import com.order.dining.dto.OrderDTO;


/**
 * @Author: baojx
 * @Date: 2020/2/12 13:54
 */
public interface PayOrderService {

    /**
     * 创建订单
     *
     * @param orderDTO 订单传输对象
     * @return 订单传输对象
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 查找订单
     *
     * @param orderId 订单id
     * @return 订单传输对象
     */
    OrderDTO selectOne(String orderId);

    /**
     * 通过buyerOpenId查找订单并分页
     *
     * @param pageRequest 分页请求
     * @param openId      buyerOpenId
     * @return 分页结果
     */
    PageResult selectByBuyerOpenId(PageRequest pageRequest, String openId);

    /**
     * 查找订单并分页
     *
     * @param pageRequest 分页请求
     * @return 分页结果
     */
    PageResult selectAll(PageRequest pageRequest);

    /**
     * 取消订单
     *
     * @param orderDTO 订单传输对象
     * @return 订单传输对象
     */
    OrderDTO cancel(OrderDTO orderDTO);

    /**
     * 关闭订单
     *
     * @param orderDTO 订单传输对象
     * @return 订单传输对象
     */
    OrderDTO close(OrderDTO orderDTO);

    /**
     * 订单支付修改状态
     *
     * @param orderDTO 订单传输对象
     * @return 订单传输对象
     */
    OrderDTO payOrder(OrderDTO orderDTO);

}
