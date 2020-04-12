package com.order.dining.beans.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * @Author: baojx
 * @Date: 2020/04/08 21:10
 * @Desc:
 */
@Data
public class ProductDto {
    private MultipartFile multipartFile;

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDesc;

    private String productIcon;

    private Integer categoryNo;

    private Integer sellCount;
}
