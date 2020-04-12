package com.order.dining.beans.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:54
 * @Desc: 商品信息返回封装
 */
@Data
public class ProductInfoVO {

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDesc;

    @JsonProperty("icon")
    private String productIcon;

    @JsonProperty("sellCount")
    private Integer sellCount;
}
