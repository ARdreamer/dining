package com.order.dining.enums;

import lombok.Getter;

/**
 * @Author: baojx
 * @Date: 2020/2/12 13:09
 */
@Getter
public enum EPayOrderStatus {
    /**
     * 未支付
     */
    NO_PAY(0, "未支付"),
    /**
     * 支付成功
     */
    SUCCESS(1, "支付成功");

    private Integer code;

    private String decs;

    EPayOrderStatus(Integer code, String decs) {
        this.code = code;
        this.decs = decs;
    }

}
