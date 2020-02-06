package com.order.dining;

import com.order.dining.dao.domain.Category;
import com.order.dining.dao.mappers.CategoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@MapperScan("com.order.dining.dao.mappers")
public class DiningApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void findOne() {
        Category category = categoryMapper.selectByPrimaryKey(1);
        System.out.println(category);

    }

}
