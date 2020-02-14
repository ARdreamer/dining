package com.order.dining.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: baojx
 * @Date: 2020/2/14 14:06
 * @Desc: 购物车
 */
@Data
@AllArgsConstructor
public class CartDTO {

    private String productId;

    //todo productNum
    private Integer productQuantity;
}
