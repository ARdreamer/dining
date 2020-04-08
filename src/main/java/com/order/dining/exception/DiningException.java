package com.order.dining.exception;

import com.order.dining.common.enums.EResultError;
import lombok.Getter;

/**
 * @Author: baojx
 * @Date: 2020/2/14 13:35
 * @Desc: 通用异常处理类
 */
@Getter
public class DiningException extends RuntimeException{

    private Integer code;

    public DiningException(EResultError resultError) {
        super(resultError.getDesc());

        this.code = resultError.getCode();
    }

    public DiningException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
