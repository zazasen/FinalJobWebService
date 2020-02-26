package com.cyrus.final_job.entity.vo;

import com.cyrus.final_job.entity.CheckIn;
import lombok.Data;

@Data
public class CheckInRecordVo extends CheckIn {
    private String startTypeStr;
    private String endTypeStr;
    private String signTypeStr;
    private String workHoursStr;
    private String username;
}
