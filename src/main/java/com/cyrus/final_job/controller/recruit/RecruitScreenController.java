package com.cyrus.final_job.controller.recruit;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.enums.ResumeStatusEnum;
import com.cyrus.final_job.service.DepartmentService;
import com.cyrus.final_job.service.PositionService;
import com.cyrus.final_job.service.ResumeService;
import com.cyrus.final_job.utils.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recruit/screen")
public class RecruitScreenController {
    @Autowired
    private PositionService positionService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ResumeService resumeService;

    @GetMapping("/getPositions")
    public Result getPositions() {
        return positionService.getPositions();
    }

    @GetMapping("/getAllDepartment")
    public Result getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @PostMapping("/getResumes")
    public ResultPage getResumes(@RequestBody JSONObject params) {
        return resumeService.getResumes(params);
    }

    @GetMapping("/getResumeStatus")
    public Result getResumeStatus() {
        return Results.createOk(ResumeStatusEnum.getResumeStatusList());
    }

    @GetMapping("/getResumeStatusStep")
    public Result getResumeStatusStep() {
        return Results.createOk(ResumeStatusEnum.getStepList());
    }

    @PostMapping("/updateResumeStatus")
    public Result updateResumeStatus(@RequestBody JSONObject params){
        return resumeService.updateResumeStatus(params);
    }
}
