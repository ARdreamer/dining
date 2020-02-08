package com.order.dining.service;

import com.order.dining.dao.domain.ProductInfo;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

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

    //TODO 改为Page Object
    List<ProductInfo> findAll(SpringDataWebProperties.Pageable pageable);

    Integer insert(ProductInfo productInfo);


}
