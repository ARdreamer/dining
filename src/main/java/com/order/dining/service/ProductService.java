package com.order.dining.service;

import com.order.dining.dao.domain.ProductInfo;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:30
 */
public interface ProductService {

    ProductInfo findOne(String productId);

    /**
     * 查询上架商品列表
     * @return 商品列表
     */
    List<ProductInfo> findOnLine();

    //TODO 改为Page Object
    List<ProductInfo> findAll(SpringDataWebProperties.Pageable pageable);

    Integer insert(ProductInfo productInfo);


}
