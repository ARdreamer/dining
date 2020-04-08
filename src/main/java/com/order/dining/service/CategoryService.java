package com.order.dining.service;

import com.order.dining.common.page.PageRequest;
import com.order.dining.common.page.PageResult;
import com.order.dining.dao.domain.Category;

import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:05
 */
public interface CategoryService {

    public Category selectOne(Integer categoryId);

    public PageResult selectAll(PageRequest pageRequest);

    public List<Category> selectAll();

    public List<Category> selectByCategoryNo(List<Integer> categoryTypeList);

    public Integer insert(Category category);

    public Integer update(Category category);
}
