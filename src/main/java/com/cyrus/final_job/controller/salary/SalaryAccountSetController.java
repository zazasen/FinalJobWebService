package com.cyrus.final_job.controller.salary;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.AccountSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 薪资账套
 */

@RestController
@RequestMapping("/salary/accountSet")
public class SalaryAccountSetController {


    @Autowired
    private AccountSetService accountSetService;

    @PostMapping("/addAccountSet")
    public Result addAccountSet(@RequestBody JSONObject params) {
        return accountSetService.addAccountSet(params);
    }


    @PostMapping("/queryAccountSet")
    public ResultPage queryAccountSet(@RequestBody JSONObject params) {
        return accountSetService.queryAccountSet(params);
    }

    @PostMapping("/editAccountSet")
    public Result editAccountSet(@RequestBody JSONObject params) {
        return accountSetService.editAccountSet(params);
    }

    @PostMapping("/delAccountSet")
    public Result delAccountSet(@RequestBody JSONObject params) {
        return accountSetService.delAccountSet(params);
    }
}
