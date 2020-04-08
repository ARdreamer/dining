package com.order.dining.common.enums;

import lombok.Getter;

/**
 * @Author: baojx
 * @Date: 2020/2/12 13:09
 * @Desc: 支付状态枚举类型
 */
@Getter
public enum EPayStatus {
    /**
     * 未支付
     */
    NO_PAY(0, "未支付"),
    /**
     * 支付成功
     */
    SUCCESS(1, "支付成功");

    private Integer code;

    private String desc;

    EPayStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static EPayStatus getEPayStatus(Integer code) {
        for (EPayStatus payStatus : EPayStatus.values()) {
            if (payStatus.getCode().equals(code)){
                return payStatus;
            }
        }
        return null;
    }
}
