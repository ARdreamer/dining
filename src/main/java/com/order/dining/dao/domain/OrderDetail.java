package com.order.dining.dao.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDetail {
    public OrderDetail(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public OrderDetail() {

    }
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_detail.detail_id
     *
     * @mbg.generated
     */
    private String detailId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_detail.order_id
     *
     * @mbg.generated
     */
    private String orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_detail.product_id
     *
     * @mbg.generated
     */
    private String productId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_detail.product_name
     *
     * @mbg.generated
     */
    private String productName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_detail.product_price
     *
     * @mbg.generated
     */
    private BigDecimal productPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_detail.product_quantity
     *
     * @mbg.generated
     */
    private Integer productQuantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_detail.product_icon
     *
     * @mbg.generated
     */
    private String productIcon;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_detail.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_detail.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

}