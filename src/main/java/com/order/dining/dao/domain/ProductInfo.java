package com.order.dining.dao.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.order.dining.enums.EPayStatus;
import com.order.dining.enums.EProductInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_info.product_id
     *
     * @mbg.generated
     */
    private String productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_info.product_name
     *
     * @mbg.generated
     */
    private String productName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_info.product_price
     *
     * @mbg.generated
     */
    private BigDecimal productPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_info.product_stock
     *
     * @mbg.generated
     */
    private Integer productStock;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_info.product_desc
     *
     * @mbg.generated
     */
    private String productDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_info.product_icon
     *
     * @mbg.generated
     */
    private String productIcon;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_info.product_status
     *
     * @mbg.generated
     */
    private Byte productStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_info.category_no
     *
     * @mbg.generated
     */
    private Integer categoryNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_info.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_info.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    @JsonIgnore
    @JSONField(serialize = false)
    public EProductInfo getProductStatusEnum() {
        return EProductInfo.getEProductInfo(Integer.valueOf(getProductStatus()));
    }


}