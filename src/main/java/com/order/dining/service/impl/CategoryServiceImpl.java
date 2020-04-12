package com.order.dining.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.order.dining.common.page.PageRequest;
import com.order.dining.common.page.PageResult;
import com.order.dining.dao.domain.Category;
import com.order.dining.dao.mappers.CategoryMapper;
import com.order.dining.service.CategoryService;
import com.order.dining.utils.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        return categoryMapper.selectAll();    }

    @Override
    public List<Category> selectByCategoryNo(List<Integer> categoryNoList) {
        return categoryMapper.selectByCategoryNo(categoryNoList);
    }

    @Override
    public Integer insert(Category category) {
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        return categoryMapper.insert(category);
    }

    @Override
    public Integer update(Category category) {
        category.setUpdateTime(new Date());
        return categoryMapper.updateByPrimaryKeySelective(category);
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
