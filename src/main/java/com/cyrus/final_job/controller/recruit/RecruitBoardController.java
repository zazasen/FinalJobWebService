package com.cyrus.final_job.controller.recruit;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.DepartmentService;
import com.cyrus.final_job.service.StaffNeedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 招聘看板
 */
@RestController
@RequestMapping("/recruit/board")
public class RecruitBoardController {

    @Autowired
    private StaffNeedsService staffNeedsService;
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/getStaffNeedsDate")
    public ResultPage getStaffNeedsDate(@RequestBody JSONObject params) {
        return staffNeedsService.getStaffNeedsDate(params);
    }

    @GetMapping("/getAllDepartment")
    public Result getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @PostMapping("/getStaffNeedsDetail")
    public Result getStaffNeedsDetail(@RequestBody JSONObject params) {
        return staffNeedsService.getStaffNeedsDetail(params);
    }

    @PostMapping("/editStaffNeeds")
    public Result editStaffNeeds(@RequestBody JSONObject params) {
        return staffNeedsService.editStaffNeeds(params);
    }
}
