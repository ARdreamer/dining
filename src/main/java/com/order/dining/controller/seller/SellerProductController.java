package com.order.dining.controller.seller;

import com.order.dining.common.PageRequest;
import com.order.dining.common.PageResult;
import com.order.dining.exception.DiningException;
import com.order.dining.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

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

    @GetMapping("on_sale")
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

    @GetMapping("off_sale")
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
}
