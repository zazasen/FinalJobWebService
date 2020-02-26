package com.cyrus.final_job.controller.attendance;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance/allRecord")
public class AttendanceAllRecordController {

    @Autowired
    private CheckInService checkInService;

    @RequestMapping("/getAllAttendanceRecord")
    public ResultPage getAllAttendanceRecord(@RequestBody JSONObject params) {
        return checkInService.getAllAttendanceRecord(params);
    }
}
