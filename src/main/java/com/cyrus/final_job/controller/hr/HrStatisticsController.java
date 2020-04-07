package com.cyrus.final_job.controller.hr;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hr/statistics")
public class HrStatisticsController {

    @Autowired
    private UserService userService;

    @PostMapping("/getStatisticsDate")
    public Result getStatisticsDate(@RequestBody JSONObject params) {
        return userService.getStatisticsDate(params);
    }

}
