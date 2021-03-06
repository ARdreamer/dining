package com.order.dining.controller.buyer;

import com.order.dining.beans.vo.ProductInfoVO;
import com.order.dining.beans.vo.ProductVO;
import com.order.dining.common.Result;
import com.order.dining.dao.domain.*;
import com.order.dining.service.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:45
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Resource
    private ProductService productService;

    @Resource
    private CategoryService categoryService;

    @GetMapping("list")
    public Result<List<ProductVO>> list() {
        //1. 查找所有上架商品
        List<ProductInfo> productInfoList = productService.selectOnLine();

        //2. 查询商品类目
        List<Integer> categoryNoList = productInfoList.stream()
                .map(ProductInfo::getCategoryNo)
                .collect(Collectors.toList());
        List<Category> categoryList = categoryService.selectByCategoryNo(categoryNoList);

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
        return new Result<>(productVOList);
    }
}
