package com.order.dining.common.enums;

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

    private String desc;

    EProductInfo(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static EProductInfo getEProductInfo(Integer code) {
        for (EProductInfo eProductInfo : EProductInfo.values()) {
            if (eProductInfo.getCode().equals(code)){
                return eProductInfo;
            }
        }
        return null;

    }
}
