package com.order.dining.service.impl;

import com.order.dining.dao.domain.Category;
import com.order.dining.dao.mappers.CategoryMapper;
import com.order.dining.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:08
 */
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public Category findOne(Integer categoryId) {
        return categoryMapper.selectByPrimaryKey(categoryId);
    }

    @Override
    public List<Category> findAll() {
        return categoryMapper.selectAll();
    }

    @Override
    public List<Category> findByCategoryNo(List<Integer> categoryNoList) {
        return categoryMapper.findByCategoryNo(categoryNoList);
    }

    @Override
    public Integer insert(Category category) {
        return categoryMapper.insert(category);
    }
}
