package com.cyrus.final_job.controller.salary;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.DepartmentService;
import com.cyrus.final_job.service.RewardAndPunishService;
import com.cyrus.final_job.service.UserAccountSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 员工奖惩
 */

@RestController
@RequestMapping("/salary/rewardAndPunish")
public class SalaryRewardAndPunishController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RewardAndPunishService rewardAndPunishService;

    @Autowired
    private UserAccountSetService userAccountSetService;

    @GetMapping("/getAllDepartment")
    public Result getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @PostMapping("/getUsers")
    public ResultPage getUserAccountSet(@RequestBody JSONObject params) {
        return userAccountSetService.getUserAccountSet(params);
    }

    @PostMapping("/queryRewardAndPunish")
    public Result queryRewardAndPunish(@RequestBody JSONObject params) {
        return rewardAndPunishService.queryRewardAndPunish(params);
    }

    @PostMapping("/addRewardAndPunish")
    public Result addRewardAndPunish(@RequestBody JSONObject params) {
        return rewardAndPunishService.addRewardAndPunish(params);
    }
}
