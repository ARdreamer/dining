package com.order.dining.beans.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
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

    @NotEmpty(message = "商品名称必填")
    private String productName;

    @NotNull(message = "价格必填")
    @DecimalMin(message = "最小价格为0.1元", value = "0.1")
    private BigDecimal productPrice;

    @NotNull(message = "库存数量必填")
    @Min(message = "库存数量不能小于0", value = 0)
    private Integer productStock;

    private String productDesc;

    private String productIcon;

    @NotNull(message = "类目名称必填")
    @Min(message = "类目名称必填", value = 0)
    private Integer categoryNo;

    private Integer sellCount;
}
