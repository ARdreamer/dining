package com.order.dining.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.order.dining.common.PageRequest;
import com.order.dining.common.PageResult;
import com.order.dining.dao.domain.ProductInfo;
import com.order.dining.dao.mappers.ProductInfoMapper;
import com.order.dining.enums.EProductInfo;
import com.order.dining.service.ProductService;
import com.order.dining.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:08
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoMapper.selectByPrimaryKey(productId);
    }

    @Override
    public List<ProductInfo> findOnLine() {
        return productInfoMapper.findByProductStatus(EProductInfo.ON_LINE.getCode().byteValue());
    }

    @Override
    public PageResult findAll(PageRequest pageRequest) {
        return PageUtil.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    @Override
    public Integer insert(ProductInfo productInfo) {
        return productInfoMapper.insert(productInfo);
    }

    /**
     * 调用分页插件完成分页
     *
     * @param pageRequest
     * @return
     */
    private PageInfo<ProductInfo> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<ProductInfo> productInfoList = productInfoMapper.findAll();
        return new PageInfo<ProductInfo>(productInfoList);
    }

}
