package com.cyrus.final_job.controller.access;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.condition.UserAccountCondition;
import com.cyrus.final_job.service.system.RoleService;
import com.cyrus.final_job.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/access/user")
public class UserManagerController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/queryUsers")
    public ResultPage queryUsers(@RequestBody JSONObject params) {
        return userService.getUsers(params);
    }

    @GetMapping("/getAllRoles")
    public Result getAllRoles() {
        return roleService.getAllRolesWithoutCondition();
    }


    @PostMapping("/updateUserAccount")
    public Result updateUserAccount(@RequestBody UserAccountCondition condition) {
        return userService.updateUserAccount(condition);
    }
}
