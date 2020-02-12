package com.order.dining.service;

import com.order.dining.common.PageRequest;
import com.order.dining.common.PageResult;
import com.order.dining.dao.domain.Order;

import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/12 13:54
 */
public interface OrderService {

    PageResult findByBuyerOpenId(PageRequest pageRequest, String openId);

}
