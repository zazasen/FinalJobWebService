package com.cyrus.final_job;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.DepartmentDao;
import com.cyrus.final_job.dao.NationDao;
import com.cyrus.final_job.dao.PoliticsStatusDao;
import com.cyrus.final_job.dao.system.MenuDao;
import com.cyrus.final_job.dao.system.RoleDao;
import com.cyrus.final_job.entity.system.Role;
import com.cyrus.final_job.service.DepartmentService;
import com.cyrus.final_job.service.system.MenuService;
import com.cyrus.final_job.utils.RedisKeys;
import com.cyrus.final_job.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.thymeleaf.TemplateEngine;

import java.sql.Timestamp;
import java.util.List;

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
    private RoleDao roleDao;

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void setValue() {
        List<Role> roles = roleDao.queryAll(new Role());
        redisUtils.set("roleTest", roles);
    }

    @Test
    public void getValue() {
        String s = RedisKeys.menusKey(1);
        String roleTest = redisUtils.get(s);
        System.out.println(roleTest);
    }


    @Test
    public void calMoney() {
        System.out.println(new Timestamp(1582992000000L).toLocalDateTime().toLocalDate());
    }

    @Autowired
    TemplateEngine templateEngine;

    @Value("${fastdfs.nginx.host}")
    private String nginxHost;


    @Test
    public void contend(){
        System.out.println(nginxHost);
    }

}
