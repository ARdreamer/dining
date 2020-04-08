package com.order.dining.service.impl;

import com.order.dining.common.enums.EResultError;
import com.order.dining.dao.domain.SellerInfo;
import com.order.dining.dao.mappers.SellerInfoMapper;
import com.order.dining.exception.DiningException;
import com.order.dining.service.SellerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: baojx
 * @Date: 2020/2/22 11:50
 * @Desc: 卖家信息
 */
@Service
@Slf4j
public class SellerInfoServiceImpl implements SellerInfoService {

    @Resource
    private SellerInfoMapper sellerInfoMapper;

    @Override
    public Integer insert(SellerInfo sellerInfo) {
        sellerInfo.setCreateTime(new Date());
        sellerInfo.setUpdateTime(new Date());
        SellerInfo sellerInfo1 = sellerInfoMapper.selectByPrimaryUsername(sellerInfo.getUsername());
        if (sellerInfo1 != null) {
            throw new DiningException(EResultError.REGISTER_ERROR);
        }
        return sellerInfoMapper.insert(sellerInfo);
    }

    @Override
    public SellerInfo login(String username, String pwd) {
        return sellerInfoMapper.selectByUsernameAndPwd(username, pwd);
    }

}
