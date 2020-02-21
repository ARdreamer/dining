package com.order.dining.service;

import com.order.dining.common.PageRequest;
import com.order.dining.common.PageResult;
import com.order.dining.dao.domain.ProductInfo;
import com.order.dining.dto.CartDTO;

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
    ProductInfo selectOne(String productId);

    /**
     * 查询上架商品列表
     *
     * @return 商品列表
     */
    List<ProductInfo> selectOnLine();

    /**
     * 分页查询商品列表
     *
     * @param pageRequest 查询请求
     * @return 查询结果
     */
    PageResult selectAll(PageRequest pageRequest);

    Integer insert(ProductInfo productInfo);

    void incrStock(List<CartDTO> cartDTOList);

    void decrStock(List<CartDTO> cartDTOList);

    ProductInfo onLine(String productId);

    ProductInfo offLine(String productId);

    Integer update(ProductInfo productInfo);
}
