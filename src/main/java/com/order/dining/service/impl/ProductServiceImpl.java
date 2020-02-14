package com.order.dining.service.impl;

import com.github.pagehelper.*;
import com.order.dining.common.*;
import com.order.dining.dao.domain.*;
import com.order.dining.dao.mappers.*;
import com.order.dining.dto.CartDTO;
import com.order.dining.enums.*;
import com.order.dining.exception.DiningException;
import com.order.dining.service.ProductService;
import com.order.dining.utils.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:08
 */
@Service
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
    public PageResult selectAll(PageRequest pageRequest) {
        return PageUtil.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    @Override
    public Integer insert(ProductInfo productInfo) {
        return productInfoMapper.insert(productInfo);
    }

    @Override
    public void incrStock(List<CartDTO> cartDTOList) {

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
            productInfoMapper.updateByPrimaryKeySelective(productInfo);
        }
    }

    /**
     * 调用分页插件完成分页
     *
     * @param pageRequest 分页请求
     * @return 分页结果
     */
    private PageInfo<ProductInfo> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<ProductInfo> productInfoList = productInfoMapper.selectAll();
        return new PageInfo<>(productInfoList);
    }

}
