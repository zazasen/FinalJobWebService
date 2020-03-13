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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        Map<String,String> map = new HashMap<>();
        map.put("hhh","1");
        redisUtil.hPutAll("hashTest",map);
        redisUtil.expire("hashTest",10, TimeUnit.SECONDS);
        Map<Object, Object> hashTest = redisUtil.hGetAll("hashTest");
        System.out.println(hashTest.get("hhh"));
    }

    @Test
    void getValue() {
        Map<Object, Object> hashTest = redisUtil.hGetAll("hashTest");
        System.out.println(hashTest);
    }


    @Test
    public void calMoney() {
        System.out.println(new Timestamp(1582992000000L).toLocalDateTime().toLocalDate());
    }

    @Autowired
    TemplateEngine templateEngine;

    @Test
    public void contend(){
        Context context = new Context();
        context.setVariable("name", "cyrus");
        String process = templateEngine.process("Contract.html", context);
        System.out.println(process);
    }

}
