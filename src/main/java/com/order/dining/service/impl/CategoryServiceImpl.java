package com.order.dining.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.order.dining.common.page.PageRequest;
import com.order.dining.common.page.PageResult;
import com.order.dining.dao.domain.Category;
import com.order.dining.dao.domain.ProductInfo;
import com.order.dining.dao.mappers.CategoryMapper;
import com.order.dining.dao.mappers.ProductInfoMapper;
import com.order.dining.service.CategoryService;
import com.order.dining.service.ProductService;
import com.order.dining.utils.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:08
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ProductInfoMapper productInfoMapper;
    @Resource
    private ProductService productService;

    @Override

    public Category selectOne(Integer categoryId) {
        return categoryMapper.selectByPrimaryKey(categoryId);
    }

    @Override
    public PageResult selectAll(PageRequest pageRequest) {
        return PageUtil.getPageResult(getPageInfo(pageRequest));
    }

    @Override
    public List<Category> selectAll() {
        return categoryMapper.selectAll();
    }

    @Override
    public List<Category> selectByCategoryNo(List<Integer> categoryNoList) {
        return categoryMapper.selectByCategoryNo(categoryNoList);
    }

    @Override
    public Integer insert(Category category) {
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());

        List<ProductInfo> productInfoList2 = productInfoMapper.selectByCategoryNo(category.getCategoryNo());
        for (ProductInfo ignored : productInfoList2) {
            productService.onLine(ignored.getProductId());
        }

        return categoryMapper.insert(category);
    }

    @Override
    public Integer update(Category category) {
        //1. 设置时间
        category.setUpdateTime(new Date());
        //2. 根据categoryId查找对象
        Category category1 = categoryMapper.selectByPrimaryKey(category.getCategoryId());
        //3. 比较现在与原来categoryNo是否相同，不同则将原来categoryNo对应商品下架，并且将现在categoryNo对应商品上架
        if (!category1.getCategoryNo().equals(category.getCategoryNo())) {
            List<ProductInfo> productInfoList1 = productInfoMapper.selectByCategoryNo(category1.getCategoryNo());
            for (ProductInfo ignored : productInfoList1) {
                productService.offLine(ignored.getProductId());
            }
            List<ProductInfo> productInfoList2 = productInfoMapper.selectByCategoryNo(category.getCategoryNo());
            for (ProductInfo ignored : productInfoList2) {
                productService.onLine(ignored.getProductId());
            }
        }
        //4. 更新并返回结果
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Integer delete(Integer categoryId, Integer categoryNo) {
        List<ProductInfo> productInfoList = productInfoMapper.selectByCategoryNo(categoryNo);
        for (ProductInfo productInfo : productInfoList) {
            productService.offLine(productInfo.getProductId());
        }
        return categoryMapper.deleteByPrimaryKey(categoryId);
    }

    /**
     * 调用分页插件完成分页
     *
     * @param pageRequest 分页请求
     * @return 分页结果
     */
    private PageInfo<Category> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Category> categoryList = categoryMapper.selectAll();
        return new PageInfo<>(categoryList);
    }

}
