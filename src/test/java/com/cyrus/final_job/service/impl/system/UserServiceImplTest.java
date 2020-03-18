package com.cyrus.final_job.service.impl.system;

import com.cyrus.final_job.dao.StaffNeedsDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.service.system.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Test
    void loadUserByUsername() {
        System.out.println(userService.loadUserByUsername("admin"));
    }

    @Autowired
    private StaffNeedsDao staffNeedsDao;

    @Test
    public void test() {
        System.out.println(staffNeedsDao.queryById(1).getResponsibility());
    }
}