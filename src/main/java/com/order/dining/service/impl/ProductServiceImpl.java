package com.order.dining.service.impl;

import com.order.dining.dao.domain.Category;
import com.order.dining.dao.domain.ProductInfo;
import com.order.dining.dao.mappers.CategoryMapper;
import com.order.dining.dao.mappers.ProductInfoMapper;
import com.order.dining.enums.EProductInfo;
import com.order.dining.service.CategoryService;
import com.order.dining.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:08
 */
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoMapper.selectByPrimaryKey(productId);
    }

    @Override
    public List<ProductInfo> findOnLine() {
        return productInfoMapper.findByProductStatus(EProductInfo.ON_LINE.getCode());
    }

    @Override
    public List<ProductInfo> findAll(SpringDataWebProperties.Pageable pageable) {
        return null;
    }

    @Override
    public Integer insert(ProductInfo productInfo) {
        return productInfoMapper.insert(productInfo);
    }
}
