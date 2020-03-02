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
@RequestMapping("/staff/myApproval")
public class StaffMyApprovalController {

    @Autowired
    private ApprovalRecordService approvalRecordService;

    /**
     * 获取当前登录用户的所有审批记录
     *
     * @return
     */
    @PostMapping("/getAllMyApproval")
    public ResultPage getAllMyApproval(@RequestBody JSONObject params) {
        // 获取当前用户审批别人的记录
        params.put("type", 1);
        return approvalRecordService.getAllMyApproval(params);
    }

    /**
     * 获取具体某一条记录的详情
     *
     * @param params
     * @return
     */
    @PostMapping("/getApprovalDetail")
    public Result getApprovalDetail(@RequestBody JSONObject params) {
        return approvalRecordService.getApprovalDetail(params);
    }

    /**
     * 审批处理
     * @param params
     * @return
     */
    @PostMapping("/approvalPass")
    public Result approvalPass(@RequestBody JSONObject params) {
        return approvalRecordService.approvalPass(params);
    }

    @PostMapping("/notPass")
    public Result notPass(@RequestBody JSONObject params) {
        return approvalRecordService.notPass(params);
    }

}
