package com.cyrus.final_job.controller.staff;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.ApprovalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff/myRecord")
public class StaffMyRecordController {

    @Autowired
    private ApprovalRecordService approvalRecordService;

    @PostMapping("/getAllMyApproval")
    public ResultPage getAllMyApproval(@RequestBody JSONObject params) {
        // 获取个人曾经的被审批记录
        params.put("type", 0);
        return approvalRecordService.getAllMyApproval(params);
    }

    @PostMapping("/getApprovalDetail")
    public Result getApprovalDetail(@RequestBody JSONObject params) {
        return approvalRecordService.getApprovalDetail(params);
    }
}
