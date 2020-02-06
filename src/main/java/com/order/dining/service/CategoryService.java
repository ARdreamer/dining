package com.order.dining.service;

import com.order.dining.dao.domain.Category;

import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:05
 */
public interface CategoryService {

    public Category findOne(Integer categoryId);

    public List<Category> findAll();

    public List<Category> findByCategoryNo(List<Integer> categoryTypeList);

    public Integer insert(Category category);
}
