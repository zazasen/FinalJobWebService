package com.cyrus.final_job.controller;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.condition.InputResumeCondition;
import com.cyrus.final_job.service.ResumeService;
import com.cyrus.final_job.service.StaffNeedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 可以不用登录访问的链接
 */

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private StaffNeedsService staffNeedsService;

    @Autowired
    private ResumeService resumeService;

    /**
     * 岗位列表（投递人可以看）
     *
     * @param params
     * @return
     */
    @PostMapping("/getPublishedStaffNeeds")
    public ResultPage getPublishedStaffNeeds(@RequestBody JSONObject params) {
        return staffNeedsService.getPublishedStaffNeeds(params);
    }

    /**
     * 岗位详情（投递人可以看）
     *
     * @param params
     * @return
     */
    @PostMapping("/getStaffNeedsDetail")
    public Result getStaffNeedsDetail(@RequestBody JSONObject params) {
        return staffNeedsService.getStaffNeedsDetail(params);
    }

    @PostMapping("/inputResume")
    public Result inputResume(InputResumeCondition condition) {
        return resumeService.inputResume(condition);

    }
}
