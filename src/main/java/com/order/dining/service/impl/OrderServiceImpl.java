package com.order.dining.service.impl;

import com.github.pagehelper.*;
import com.order.dining.common.*;
import com.order.dining.dao.domain.*;
import com.order.dining.dao.mappers.*;
import com.order.dining.service.*;
import com.order.dining.utils.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @Author: baojx
 * @Date: 2020/2/12 13:54
 */
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public PageResult findByBuyerOpenId(PageRequest pageRequest, String openId) {
        return PageUtil.getPageResult(pageRequest, getPageInfo(pageRequest, openId));
    }

    /**
     * 调用分页插件完成分页
     *
     * @param pageRequest 分页请求
     * @param openId      用户openId
     * @return 分页信息
     */
    private PageInfo<Order> getPageInfo(PageRequest pageRequest, String openId) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.findByBuyerOpenId(openId);
        return new PageInfo<>(orderList);
    }
}
