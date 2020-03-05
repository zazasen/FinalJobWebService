package com.cyrus.final_job.controller.staff;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.ApprovalRecordService;
import com.cyrus.final_job.service.LeaveService;
import com.cyrus.final_job.service.OvertimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 我的申请
 */
@RestController
@RequestMapping("/staff/myRecord")
public class StaffMyRecordController {

    @Autowired
    private ApprovalRecordService approvalRecordService;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private OvertimeService overtimeService;

    @PostMapping("/getAllMyApproval")
    public ResultPage getAllMyApproval(@RequestBody JSONObject params) {
        // 获取个人曾经的被审批记录
        params.put("type", 0);
        return approvalRecordService.getAllMyApproval(params);
    }

    /**
     * 查看申请详细信息
     *
     * @param params
     * @return
     */
    @PostMapping("/getApprovalDetail")
    public Result getApprovalDetail(@RequestBody JSONObject params) {
        return approvalRecordService.getApprovalDetail(params);
    }

    /**
     * 请假申请
     *
     * @param params
     * @return
     */
    @PostMapping("/leaveApply")
    public Result leaveApply(@RequestBody JSONObject params) {
        return leaveService.leaveApply(params);
    }

    /**
     * 加班申请
     *
     * @param params
     * @return
     */
    @PostMapping("/overtimeApply")
    public Result overtimeApply(@RequestBody JSONObject params) {
        return overtimeService.overtimeApply(params);
    }
}
