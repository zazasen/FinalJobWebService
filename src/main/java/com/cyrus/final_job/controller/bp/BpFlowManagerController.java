package com.cyrus.final_job.controller.bp;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.ApprovalFlowService;
import com.cyrus.final_job.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bp/flow")
public class BpFlowManagerController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ApprovalFlowService approvalFlowService;

    @PostMapping("/getDepByBp")
    public Result getDepByBp() {
        return departmentService.getDepByBp();
    }

    @PostMapping("/getBrotherAndFatherDepStaff")
    public Result getBrotherAndFatherDepStaff(@RequestBody JSONObject params) {
        return departmentService.getBrotherAndFatherDepStaff(params);
    }

    @PostMapping("/changeApprovalFlow")
    public Result changeApprovalFlow(@RequestBody JSONObject params){
        return approvalFlowService.changeApprovalFlow(params);
    }
}
