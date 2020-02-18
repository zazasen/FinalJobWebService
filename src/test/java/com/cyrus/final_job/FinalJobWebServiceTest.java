package com.cyrus.final_job;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.system.MenuDao;
import com.cyrus.final_job.service.system.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinalJobWebServiceTest {

    @Autowired
    MenuDao menuDao;

    @Autowired
    MenuService menuService;

    @Test
    public void commonTest() {

        System.out.println(JSONObject.toJSONString(menuDao.getMenusByUserId(1)));
    }

    @Test
    public void getAllMenuWithRoleTest(){
        System.out.println(JSONObject.toJSONString(menuService.getAllMenuWithRole()));
    }

}
