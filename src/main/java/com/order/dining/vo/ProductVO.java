package com.order.dining.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:52
 */
@Data
public class ProductVO {

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryNo;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;


}
