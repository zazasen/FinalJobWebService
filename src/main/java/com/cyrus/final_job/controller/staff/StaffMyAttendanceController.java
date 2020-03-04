package com.cyrus.final_job.controller.staff;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 我的考勤
 */
@RestController
@RequestMapping("/staff/myAttendance")
public class StaffMyAttendanceController {

    @Autowired
    private CheckInService checkInService;

    @GetMapping("/calendarShow")
    public Result calendarShow() {
        return checkInService.calendarShow();
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

    /**
     * 获取个人考勤记录
     * @param params
     * @return
     */
    @PostMapping("/getAttendanceRecord")
    public ResultPage getAttendanceRecord(@RequestBody JSONObject params) {
        return checkInService.getAttendanceRecord(params);
    }

    @PostMapping("/signIn")
    public Result signIn() {
        return checkInService.signIn();
    }

    @PostMapping("/signOut")
    public Result signOut() {
        return checkInService.signOut();
    }

    @GetMapping("/signType")
    public Result signType() {
        return checkInService.signType();
    }
}
