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
     * 订单终态
     */
    CLOSE(1, "完成"),
    /**
     * 订单取消状态
     * 订单终态
     */
    CANCEL(2, "已取消");

    private Integer code;

    private String desc;

    EOrderStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static EOrderStatus getEOrderStatus(Integer code) {
        for (EOrderStatus orderStatus : EOrderStatus.values()) {
            if (orderStatus.getCode().equals(code)){
                return orderStatus;
            }
        }
        return null;
    }

}
