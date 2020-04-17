package com.order.dining.service;


import com.order.dining.dao.domain.SellerInfo;

/**
 * @Author: baojx
 * @Date: 2020/2/22 11:49
 * @Desc: 卖家信息
 */
public interface SellerInfoService {


    Integer insert(SellerInfo sellerInfo);

    SellerInfo login(String username, String pwd);

    boolean pwdReset(SellerInfo user, String nowPwd);
}
