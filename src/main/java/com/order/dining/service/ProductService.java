package com.order.dining.service;

import com.order.dining.common.PageRequest;
import com.order.dining.common.PageResult;
import com.order.dining.dao.domain.ProductInfo;

import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:30
 */
public interface ProductService {

    /**
     * 根据商品id查询商品信息
     *
     * @param productId 商品id
     * @return 商品信息
     */
    ProductInfo findOne(String productId);

    /**
     * 查询上架商品列表
     *
     * @return 商品列表
     */
    List<ProductInfo> findOnLine();

    /**
     * 分页查询商品列表
     *
     * @param pageRequest 查询请求
     * @return 查询结果
     */
    PageResult findAll(PageRequest pageRequest);

    Integer insert(ProductInfo productInfo);

}
