package com.order.dining.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: baojx
 * @Date: 2020/2/15 14:07
 * @Desc: 前端传order表单
 */
@Data
public class OrderForm {

    /**
     * 买家姓名
     */
    @NotEmpty(message = "买家姓名必填")
    private String name;
    /**
     * 买家手机号
     */
    @NotEmpty(message = "买家手机号必填")
    private String phone;
    /**
     * 买家地址
     */
    @NotEmpty(message = "买家地址必填")
    private String address;
    /**
     * 买家openid
     */
    @NotEmpty(message = "买家openid必填")
    private String openid;
    /**
     * 买家购物车信息
     */
    @NotEmpty(message = "买家购物车信息不能为空")
    private String items;
}
