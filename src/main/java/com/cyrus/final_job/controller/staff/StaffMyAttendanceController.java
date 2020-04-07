package com.cyrus.final_job.controller.staff;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.CheckInService;
import com.cyrus.final_job.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 我的考勤
 */
@RestController
@RequestMapping("/staff/myAttendance")
public class StaffMyAttendanceController {

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private LeaveService leaveService;
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
     *
     * @param params
     * @return
     */
    @PostMapping("/getAttendanceRecord")
    public ResultPage getAttendanceRecord(@RequestBody JSONObject params) {
        return checkInService.getAttendanceRecord(params);
    }

    @PostMapping("/getShouldBeWorkDays")
    public Result getShouldBeWorkDays(@RequestBody JSONObject params) {
        return checkInService.getShouldBeWorkDays(params);
    }

    /**
     * 获取异常考勤,迟到早退情况
     *
     * @return
     */
    @PostMapping("/getExceptionCheckIn")
    public Result getExceptionCheckIn(@RequestBody JSONObject params) {
        return checkInService.getExceptionCheckIn(params);
    }

    /**
     * 获取请假信息
     *
     * @param params
     * @return
     */
    @PostMapping("/getLeaveInfo")
    public Result getLeaveInfo(@RequestBody JSONObject params) {
        return leaveService.getLeaveInfo(params);
    }
}
