package com.cyrus.final_job.entity.vo;

import com.cyrus.final_job.entity.Leave;
import lombok.Data;

@Data
public class LeaveVo extends Leave {
    private String realName;
    private String holidayTypeStr;
}
