package com.order.dining;

import com.order.dining.dao.domain.Category;
import com.order.dining.dao.mappers.CategoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
@MapperScan("com.order.dining.dao.mappers")
public class DiningApplicationTests {

    @Resource
    private CategoryMapper categoryMapper;

    @Test
    public void findOne() {
        Category category = categoryMapper.selectByPrimaryKey(1);
        System.out.println(category);
        category.setCategoryName("榜单");
        categoryMapper.updateByPrimaryKey(category);
        Category category1 = categoryMapper.selectByPrimaryKey(1);
        category1.setUpdateTime(new Date());
        System.out.println(category1);


    }

}
