package com.order.dining.dto;

import com.order.dining.dao.domain.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/14 13:08
 * @Desc: Order数据传输对象
 */
@Data
public class OrderDTO {
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Byte orderStatus;

    private Byte payStatus;

    private Date createTime;

    private Date updateTime;

    List<OrderDetail> orderDetailList;
}