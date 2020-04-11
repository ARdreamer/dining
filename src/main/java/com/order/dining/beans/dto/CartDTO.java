package com.order.dining.beans.dto;

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

    private Integer productQuantity;
}
