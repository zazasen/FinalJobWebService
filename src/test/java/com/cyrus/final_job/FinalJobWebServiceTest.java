package com.cyrus.final_job;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.system.MenuDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class FinalJobWebServiceTest {

    @Autowired
    MenuDao menuDao;

    @Test
    public void commonTest() {

        System.out.println(JSONObject.toJSONString(menuDao.getMenusByUserId(1)));
    }


}
