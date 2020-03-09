package com.cyrus.final_job.controller;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.CheckInService;
import com.cyrus.final_job.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/getShouldBeWorkDays")
    public Result getShouldBeWorkDays() {
        return checkInService.getShouldBeWorkDays();
    }


    @PostMapping("/getLeaveInfo")
    public Result getLeaveInfo(){
        return leaveService.getLeaveInfo();
    }

}
