package com.order.dining.service.impl;

import com.order.dining.dao.domain.Category;
import com.order.dining.dao.mappers.CategoryMapper;
import com.order.dining.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:08
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Category selectOne(Integer categoryId) {
        return categoryMapper.selectByPrimaryKey(categoryId);
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
        return categoryMapper.insert(category);
    }
}
