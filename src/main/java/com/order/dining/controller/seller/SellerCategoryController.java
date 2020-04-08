package com.order.dining.controller.seller;

import com.order.dining.common.page.PageRequest;
import com.order.dining.common.page.PageResult;
import com.order.dining.dao.domain.Category;
import com.order.dining.exception.DiningException;
import com.order.dining.beans.form.CategoryForm;
import com.order.dining.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: baojx
 * @Date: 2020/2/21 14:28
 * @Desc: 卖家类目
 */
@Controller
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page, size);
        PageResult pageResult = categoryService.selectAll(pageRequest);
        map.put("categoryPage", pageResult);
        map.put("currentPage", page);
        map.put("size", size);

        return new ModelAndView("category/list", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId, Map<String, Object> map) {
        if (categoryId != null) {
            Category category = categoryService.selectOne(categoryId);
            map.put("category", category);
        }

        return new ModelAndView("category/index", map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm, BindingResult bindingResult, Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }

        //保存和更新同一个接口，通过是否存在判断categoryId判断调用方法
        Category category = new Category();
        BeanUtils.copyProperties(categoryForm, category);
        try {
            //判断categoryId,是否为新增
            if (null != category.getCategoryId()) {
                categoryService.update(category);
            } else {
                categoryService.insert(category);
            }
        } catch (DiningException e) {
            log.error("【类目保存】类目保存失败");
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("common/success", map);
    }
}