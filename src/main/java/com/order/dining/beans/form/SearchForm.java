package com.order.dining.beans.form;

import lombok.Data;

/**
 * @Author: baojx
 * @Date: 2020/04/10 16:25
 * @Desc: 搜索
 */
@Data
public class SearchForm {
    private String orderId;
    private String username;
    private String phone;
    private String dateDay;
    private String dateMonth;
}
