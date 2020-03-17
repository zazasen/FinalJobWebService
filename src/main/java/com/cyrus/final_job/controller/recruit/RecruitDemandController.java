package com.cyrus.final_job.controller.recruit;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.DepartmentService;
import com.cyrus.final_job.service.PositionService;
import com.cyrus.final_job.service.StaffNeedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 招聘需求
 */
@RestController
@RequestMapping("/recruit/demand")
public class RecruitDemandController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private StaffNeedsService staffNeedsService;

    @GetMapping("/getAllDepartment")
    public Result getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @GetMapping("/getPositions")
    public Result getPositions() {
        return positionService.getPositions();
    }

    @PostMapping("/submitStaffNeed")
    public Result submitStaffNeed(@RequestBody JSONObject params) {
        return staffNeedsService.submitStaffNeed(params);
    }
}
