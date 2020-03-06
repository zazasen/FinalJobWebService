package com.cyrus.final_job;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.DepartmentDao;
import com.cyrus.final_job.dao.NationDao;
import com.cyrus.final_job.dao.PoliticsStatusDao;
import com.cyrus.final_job.dao.system.MenuDao;
import com.cyrus.final_job.service.DepartmentService;
import com.cyrus.final_job.service.system.MenuService;
import com.cyrus.final_job.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class FinalJobWebServiceTest {

    @Autowired
    MenuDao menuDao;

    @Autowired
    MenuService menuService;

    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    NationDao nationDao;

    @Autowired
    private PoliticsStatusDao politicsStatusDao;

    @Test
    public void commonTest() {

        System.out.println(JSONObject.toJSONString(menuDao.getMenusByUserId(1)));
    }

    @Test
    public void getAllMenuWithRoleTest() {
        System.out.println(JSONObject.toJSONString(menuService.getAllMenuWithRole()));
    }

    @Test
    public void getDepartmentByParentIdTest() {
        System.out.println(JSONObject.toJSONString(departmentDao.getDepartmentByParentId(-1)));
    }

    @Autowired
    DepartmentService departmentService;

    @Test
    public void getAllDepartment() {
        System.out.println(JSONObject.toJSONString(departmentService.getAllDepartment()));
    }

    @Autowired
    StringRedisTemplate template;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void setValue() {

    }

    @Test
    void getValue() {
        System.out.println(redisUtil.get("name"));
    }


    @Test
    public void calMoney() {

    }

}
