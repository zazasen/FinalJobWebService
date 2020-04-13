package com.cyrus.final_job.entity.vo;

import com.cyrus.final_job.entity.ReserveStaff;
import lombok.Data;

@Data
public class ReserveStaffVo extends ReserveStaff{
    private String departmentName;
    private String positionName;
    private String statusStr;
    private String entryTimeStr;
}
