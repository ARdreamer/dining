package com.order.dining.beans.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: baojx
 * @Date: 2020/2/21 13:13
 * @Desc: save商品
 */
@Data
public class ProductForm {

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDesc;

    private String productIcon;

    private Integer categoryNo;

}
