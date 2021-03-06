package com.order.dining.dao.mappers;

import com.order.dining.beans.form.ProductSearchForm;
import com.order.dining.dao.domain.ProductInfo;

import java.util.List;

public interface ProductInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String productId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_info
     *
     * @mbg.generated
     */
    int insert(ProductInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_info
     *
     * @mbg.generated
     */
    int insertSelective(ProductInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_info
     *
     * @mbg.generated
     */
    ProductInfo selectByPrimaryKey(String productId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ProductInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ProductInfo record);

    List<ProductInfo> selectByProductStatus(Byte productStatus);

    List<ProductInfo> selectAll();

    List<ProductInfo> selectByCategoryNo(Integer categoryNo);

    List<ProductInfo> searchByForm(ProductSearchForm productSearchForm);
}