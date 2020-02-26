package com.cyrus.final_job.controller.attendance;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance/sign")
public class AttendanceSignController {

    @Autowired
    private CheckInService checkInService;

    @PostMapping("/signIn")
    public Result signIn(){
        return checkInService.signIn();
    }

    @PostMapping("/signOut")
    public Result signOut(){
        return checkInService.signOut();
    }

    @GetMapping("/signType")
    public Result signType(){
        return checkInService.signType();
    }

    @GetMapping("/calendarShow")
    public Result calendarShow(){
        return checkInService.calendarShow();
    }
}
