package com.order.dining.common.enums;

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
     * <p>
     * 因为订餐系统，所以可以在做一个task
     * 在支付成功状态两小时后，可以进行扫表，将订单状态置为完成（也就是不能取消，不能退款了）
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
            if (orderStatus.getCode().equals(code)) {
                return orderStatus;
            }
        }
        return null;
    }

}
