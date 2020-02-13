package com.order.dining.enums;

import lombok.Getter;

/**
 * @Author: baojx
 * @Date: 2020/2/12 13:04
 * @Desc: 订单状态枚举类型
 */
@Getter
public enum EOrderStatus {
    /**
     * 新订单
     */
    NEW(0, "新订单"),
    /**
     * 订单完成状态
     */
    FINISHED(0, "完成"),
    /**
     * 订单取消状态
     */
    CANCEL(0, "已取消");

    private Integer code;

    private String decs;

    EOrderStatus(Integer code, String decs) {
        this.code = code;
        this.decs = decs;
    }

}
