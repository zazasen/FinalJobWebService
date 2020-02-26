package com.cyrus.final_job.entity.vo;

import com.cyrus.final_job.entity.ApprovalFlow;
import lombok.Data;

@Data
public class ApprovalFlowVo extends ApprovalFlow {
    private String firstApprovalManName;
    private String secondApprovalManName;
    private String thirdApprovalManName;

    public static ApprovalFlowVo buildApprovalFlowVo(ApprovalFlow approvalFlow) {
        ApprovalFlowVo approvalFlowVo = new ApprovalFlowVo();
        approvalFlowVo.setId(approvalFlow.getId());
        approvalFlowVo.setFirstApprovalMan(approvalFlow.getFirstApprovalMan());
        approvalFlowVo.setSecondApprovalMan(approvalFlow.getSecondApprovalMan());
        approvalFlowVo.setThirdApprovalMan(approvalFlow.getThirdApprovalMan());
        approvalFlowVo.setFlowType(approvalFlow.getFlowType());
        return approvalFlowVo;
    }
}
