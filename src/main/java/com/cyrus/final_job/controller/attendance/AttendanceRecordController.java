package com.cyrus.final_job.controller.attendance;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance/record")
public class AttendanceRecordController {
    @Autowired
    private CheckInService checkInService;

    @PostMapping("/getAttendanceRecord")
    public ResultPage getAttendanceRecord(@RequestBody JSONObject params) {
        return checkInService.getAttendanceRecord(params);
    }

    /**
     * 补卡（签到补卡、签退补卡）
     *
     * @param params
     * @return
     */
    @PostMapping("/remedySign")
    public Result remedySign(@RequestBody JSONObject params) {
        return checkInService.remedySign(params);
    }
}
