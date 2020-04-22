package com.order.dining.beans.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: baojx
 * @Date: 2020/2/21 14:30
 * @Desc: 类目表单
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    @NotEmpty(message = "商品类目名称必填")
    private String categoryName;

    @NotNull(message = "商品类目no必填")
    @Min(message = "商品类目no不能小于0", value = 0)
    private Integer categoryNo;

}
