package com.order.dining.service.impl;

import com.github.pagehelper.*;
import com.order.dining.beans.form.OrderSearchForm;
import com.order.dining.beans.form.ProductSearchForm;
import com.order.dining.common.page.PageRequest;
import com.order.dining.common.page.PageResult;
import com.order.dining.dao.domain.*;
import com.order.dining.dao.mappers.*;
import com.order.dining.beans.dto.CartDTO;
import com.order.dining.common.enums.*;
import com.order.dining.exception.DiningException;
import com.order.dining.service.ProductService;
import com.order.dining.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:08
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductInfoMapper productInfoMapper;

    @Override
    public ProductInfo selectOne(String productId) {
        return productInfoMapper.selectByPrimaryKey(productId);
    }

    @Override
    public List<ProductInfo> selectOnLine() {
        return productInfoMapper.selectByProductStatus(EProductInfo.ON_LINE.getCode().byteValue());
    }

    @Override
    public PageResult selectAll(PageRequest pageRequest, ProductSearchForm productSearchForm) {
        ProductSearchForm productSearchForm1 = new ProductSearchForm();
        BeanUtils.copyProperties(productSearchForm, productSearchForm1);

        doVerifyDate(productSearchForm1);

        return PageUtil.getPageResult(getPageInfo(pageRequest, productSearchForm1));
    }
    /**
     * 对前端传来的数据
     *
     * @param productSearchForm 查询表单
     */
    private void doVerifyDate(ProductSearchForm productSearchForm) {
        if (StringUtils.isNotBlank(productSearchForm.getProductName())) {
            StringBuilder sb = new StringBuilder();
            sb.append("%");
            sb.append(productSearchForm.getProductName());
            sb.append("%");
            productSearchForm.setProductName(sb.toString());
        }else {
            productSearchForm.setProductName(null);
        }
        if (StringUtils.isBlank(productSearchForm.getCategoryNo())) {
            productSearchForm.setCategoryNo(null);
        }
        if (StringUtils.isBlank(productSearchForm.getProductId())) {
            productSearchForm.setProductId(null);
        }
    }

    @Override
    public Integer insert(ProductInfo productInfo) {
        productInfo.setCreateTime(new Date());
        productInfo.setUpdateTime(new Date());

        return productInfoMapper.insert(productInfo);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void incrStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(cartDTO.getProductId());
            if (productInfo == null) {
                throw new DiningException(EResultError.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setSellCount(productInfo.getSellCount() - cartDTO.getProductQuantity());
            productInfo.setProductStock(result);
            productInfo.setUpdateTime(new Date());
            productInfoMapper.updateByPrimaryKeySelective(productInfo);
        }

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void decrStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(cartDTO.getProductId());
            if (productInfo == null) {
                throw new DiningException(EResultError.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new DiningException(EResultError.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfo.setSellCount(productInfo.getSellCount() + cartDTO.getProductQuantity());
            productInfo.setUpdateTime(new Date());
            productInfoMapper.updateByPrimaryKeySelective(productInfo);
        }
    }

    @Override
    public ProductInfo onLine(String productId) {
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);

        if (null == productInfo) {
            log.error("【商品上架】查询不到指定商品，productId=【{}】", productId);
            throw new DiningException(EResultError.PRODUCT_NOT_EXIST);
        }

//        if (productInfo.getProductStatus().equals(EProductInfo.ON_LINE.getCode().byteValue())) {
//            log.error("【商品上架】指定商品已经上架，productId=【{}】", productId);
//            throw new DiningException(EResultError.PRODUCT_STATUS_ERROR);
//        }
        productInfo.setProductStatus(EProductInfo.ON_LINE.getCode().byteValue());
        productInfo.setUpdateTime(new Date());
        productInfoMapper.updateByPrimaryKeySelective(productInfo);
        return productInfo;
    }

    @Override
    public ProductInfo offLine(String productId) {
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);

        if (null == productInfo) {
            log.error("【商品下架】查询不到指定商品，productId=【{}】", productId);
            throw new DiningException(EResultError.PRODUCT_NOT_EXIST);
        }

//        if (productInfo.getProductStatus().equals(EProductInfo.OFF_LINE.getCode().byteValue())) {
//            log.error("【商品下架】指定商品已经下架，productId=【{}】", productId);
//            throw new DiningException(EResultError.PRODUCT_STATUS_ERROR);
//        }
        productInfo.setProductStatus(EProductInfo.OFF_LINE.getCode().byteValue());
        productInfo.setUpdateTime(new Date());
        productInfoMapper.updateByPrimaryKeySelective(productInfo);
        return productInfo;
    }

    @Override
    public Integer update(ProductInfo productInfo) {
        productInfo.setUpdateTime(new Date());
        return productInfoMapper.updateByPrimaryKeySelective(productInfo);
    }

    @Override
    public Integer delete(String productId) {
        return productInfoMapper.deleteByPrimaryKey(productId);
    }

    /**
     * 调用分页插件完成分页
     *
     * @param pageRequest       分页请求
     * @param productSearchForm 搜索请求
     * @return 分页结果
     */
    private PageInfo<ProductInfo> getPageInfo(PageRequest pageRequest, ProductSearchForm productSearchForm) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<ProductInfo> productInfoList = productInfoMapper.searchByForm(productSearchForm);
        log.error("{}",productInfoList);
        return new PageInfo<>(productInfoList);
    }

}
