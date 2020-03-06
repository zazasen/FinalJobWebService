package com.cyrus.final_job.controller.salary;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.AccountSetService;
import com.cyrus.final_job.service.DepartmentService;
import com.cyrus.final_job.service.UserAccountSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 员工账套
 */

@RestController
@RequestMapping("/salary/userAccountSet")
public class SalaryUserAccountSetController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserAccountSetService userAccountSetService;

    @Autowired
    private AccountSetService accountSetService;

    @GetMapping("/getAllDepartment")
    public Result getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @PostMapping("/getUserAccountSet")
    public ResultPage getUserAccountSet(@RequestBody JSONObject params) {
        return userAccountSetService.getUserAccountSet(params);
    }

    /**
     * 获取所有薪资账套的下拉列表
     */
    @PostMapping("/getAccountSet")
    public Result getAccountSet() {
        return accountSetService.getAccountSet();
    }

    @PostMapping("/updateAccount")
    public Result updateUserAccountSet(@RequestBody JSONObject params) {
        return userAccountSetService.updateUserAccountSet(params);
    }
}
