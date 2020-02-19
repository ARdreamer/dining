package com.order.dining.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.order.dining.converter.Date2LongSerializer;
import com.order.dining.dao.domain.OrderDetail;
import com.order.dining.enums.EOrderStatus;
import com.order.dining.enums.EPayStatus;
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

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;

    @JsonIgnore
    @JSONField(serialize = false)
    public EPayStatus getPayStatusEnum() {
        return EPayStatus.getEPayStatus(Integer.valueOf(getPayStatus()));
    }
    @JsonIgnore
    @JSONField(serialize = false)
    public EOrderStatus getOrderStatusEnum() {
        return EOrderStatus.getEOrderStatus(Integer.valueOf(getOrderStatus()));
    }
}
