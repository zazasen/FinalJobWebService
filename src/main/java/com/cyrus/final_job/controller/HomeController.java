package com.cyrus.final_job.controller;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.CheckInService;
import com.cyrus.final_job.service.LeaveService;
import com.cyrus.final_job.service.StaffNeedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主页 controller
 */

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private StaffNeedsService staffNeedsService;

    @PostMapping("/getShouldBeWorkDays")
    public Result getShouldBeWorkDays() {
        return checkInService.getShouldBeWorkDays();
    }


    /**
     * 获取异常考勤,迟到早退情况
     *
     * @return
     */
    @PostMapping("/getExceptionCheckIn")
    public Result getExceptionCheckIn() {
        return checkInService.getExceptionCheckIn();
    }

    @PostMapping("/getLeaveInfo")
    public Result getLeaveInfo() {
        return leaveService.getLeaveInfo();
    }

    @PostMapping("/getPublishedStaffNeeds")
    public ResultPage getPublishedStaffNeeds(@RequestBody JSONObject params) {
        return staffNeedsService.getPublishedStaffNeeds(params);
    }

    @PostMapping("/getStaffNeedsDetail")
    public Result getStaffNeedsDetail(@RequestBody JSONObject params) {
        return staffNeedsService.getStaffNeedsDetail(params);
    }
}
