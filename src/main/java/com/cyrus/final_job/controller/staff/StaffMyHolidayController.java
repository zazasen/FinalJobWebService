package com.cyrus.final_job.controller.staff;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.HolidayService;
import com.cyrus.final_job.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff/myHoliday")
public class StaffMyHolidayController {

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private LeaveService leaveService;

    @PostMapping("/getMyHolidays")
    public Result getMyHolidays(@RequestBody JSONObject params) {
        return holidayService.getMyHolidays(params);
    }


    @PostMapping("/leaveApply")
    public Result leaveApply(@RequestBody JSONObject params){
        return leaveService.leaveApply(params);
    }

}
