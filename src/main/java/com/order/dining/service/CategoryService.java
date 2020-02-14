package com.order.dining.service;

import com.order.dining.dao.domain.Category;

import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:05
 */
public interface CategoryService {

    public Category selectOne(Integer categoryId);

    public List<Category> selectAll();

    public List<Category> selectByCategoryNo(List<Integer> categoryTypeList);

    public Integer insert(Category category);
}
