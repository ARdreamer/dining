package com.order.dining.controller.seller;

import com.order.dining.common.page.PageRequest;
import com.order.dining.common.page.PageResult;
import com.order.dining.dao.domain.*;
import com.order.dining.exception.DiningException;
import com.order.dining.form.ProductForm;
import com.order.dining.service.*;
import com.order.dining.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * @Date: 2020/2/20 12:53
 * @Desc: 买家商品
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Resource
    private ProductService productService;

    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page, size);
        PageResult pageResult = productService.selectAll(pageRequest);

        map.put("productInfoPage", pageResult);
        map.put("currentPage", page);
        map.put("size", size);

        return new ModelAndView(("product/list"), map);
    }

    @GetMapping("/on_sale")
    public ModelAndView onLine(@RequestParam("productId") String productId, Map<String, Object> map) {
        try {
            productService.onLine(productId);
        } catch (DiningException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/off_sale")
    public ModelAndView offLine(@RequestParam("productId") String productId, Map<String, Object> map) {
        try {
            productService.offLine(productId);
        } catch (DiningException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId, Map<String, Object> map) {
        if (StringUtils.isNotBlank(productId)) {
            ProductInfo productInfo = productService.selectOne(productId);
            map.put("productInfo", productInfo);
        }
        List<Category> categoryList = categoryService.selectAll();
        map.put("categoryList", categoryList);

        return new ModelAndView("product/index", map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm, BindingResult bindingResult, Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        //保存和更新同一个接口，通过productId判断调用方法
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(productForm, productInfo);
        try {
            //判断productId,是否为新增
            if (StringUtils.isBlank(productInfo.getProductId())) {
                productInfo.setProductId(KeyUtil.genUniqueKey());
                productService.insert(productInfo);
            } else {
                productService.update(productInfo);
            }
        } catch (DiningException e) {
            log.error("【商品保存】商品保存失败");
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }
}
