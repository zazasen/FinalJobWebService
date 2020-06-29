package com.cyrus.final_job.controller.hr;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.DepartmentService;
import com.cyrus.final_job.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hr/statistics")
public class HrStatisticsController {

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/getStatisticsDate")
    public Result getStatisticsDate(@RequestBody JSONObject params) {
        return userService.getStatisticsDate(params);
    }

    @GetMapping("/getAllDepartment")
    public Result getAllDepartment() {
        return departmentService.getAllDepartment();
    }

}
