package com.order.dining.dao.mappers;

import com.order.dining.dao.domain.OrderDetail;

import java.util.List;

public interface OrderDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_detail
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String detailId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_detail
     *
     * @mbg.generated
     */
    int insert(OrderDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_detail
     *
     * @mbg.generated
     */
    int insertSelective(OrderDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_detail
     *
     * @mbg.generated
     */
    OrderDetail selectByPrimaryKey(String detailId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_detail
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(OrderDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_detail
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(OrderDetail record);

    List<OrderDetail> findByOrderId(String orderId);
}