package com.cyrus.final_job.controller.attendance;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.CheckInService;
import com.cyrus.final_job.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance/statistics")
public class AttendanceStatisticsController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CheckInService checkInService;

    @GetMapping("/getAllDepartment")
    public Result getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @PostMapping("/getStatisticsDate")
    public Result getStatisticsDate(@RequestBody JSONObject params) {
        return checkInService.getStatisticsDate(params);
    }
}
