package com.cyrus.final_job.controller.staff;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff/mySalary")
public class StaffMySalaryController {

    @Autowired
    private SalaryService salaryService;

    @RequestMapping("/getMySalary")
    public ResultPage getMySalary(@RequestBody JSONObject params){
        return salaryService.getMySalary(params);
    }

}
