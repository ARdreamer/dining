package com.order.dining.enums;

import lombok.Getter;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:37
 * @Desc: 商品信息枚举类型
 */
@Getter
public enum EProductInfo {

    /**
     * 商品已上架
     */
    ON_LINE(0, "上架"),
    /**
     * 商品已下架
     */
    OFF_LINE(1, "下架");

    private Integer code;

    private String decs;

    EProductInfo(Integer code, String decs) {
        this.code = code;
        this.decs = decs;
    }

}
