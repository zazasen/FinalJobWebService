package com.cyrus.final_job.entity.vo;

import com.cyrus.final_job.entity.ApprovalRecord;
import lombok.Data;

@Data
public class ApprovalRecordVo extends ApprovalRecord {
    private String produceUserName;
    private String approvalUserName;
    private String approvalTypeStr;
    private String recordStatusStr;
}
