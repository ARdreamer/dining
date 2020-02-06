package com.order.dining.controller.buyer;

import com.order.dining.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:45
 */
@RestController
@RequestMapping("/buyer/product")
public class ProductController {

    @GetMapping("list")
    public Result list(){
        Result result = new Result();
        return result;
    }
}
