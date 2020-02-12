package com.order.dining.controller.buyer;

import com.order.dining.common.Constants;
import com.order.dining.common.Result;
import com.order.dining.dao.domain.Category;
import com.order.dining.dao.domain.ProductInfo;
import com.order.dining.service.CategoryService;
import com.order.dining.service.ProductService;
import com.order.dining.utils.ResultUtil;
import com.order.dining.vo.ProductInfoVO;
import com.order.dining.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:45
 */
@RestController
@RequestMapping("/buyer/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public Result list() {
        //1. 查找所有上架商品
        List<ProductInfo> productInfoList = productService.findOnLine();

        //2. 查询商品类目
        List<Integer> categoryNoList = productInfoList.stream()
                .map(e -> e.getCategoryNo())
                .collect(Collectors.toList());
        List<Category> categoryList = categoryService.findByCategoryNo(categoryNoList);

        //3. 拼装数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (Category category : categoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(category.getCategoryName());
            productVO.setCategoryNo(category.getCategoryNo());
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryNo().equals(category.getCategoryNo())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //拷贝数据到productInfoVO
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultUtil.success(productVOList);
    }
}
