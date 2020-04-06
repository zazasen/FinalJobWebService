package com.cyrus.final_job.controller.attendance;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.CheckInService;
import com.cyrus.final_job.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance/allRecord")
public class AttendanceAllRecordController {

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/getAllAttendanceRecord")
    public ResultPage getAllAttendanceRecord(@RequestBody JSONObject params) {
        return checkInService.getAllAttendanceRecord(params);
    }

    @GetMapping("/getAllDepartment")
    public Result getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @PostMapping("/updateCheckIn")
    public Result updateCheckIn(@RequestBody JSONObject params) {
        return checkInService.updateCheckIn(params);
    }
}
