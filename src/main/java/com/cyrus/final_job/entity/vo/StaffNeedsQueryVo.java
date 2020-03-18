package com.cyrus.final_job.entity.vo;

import com.cyrus.final_job.entity.StaffNeeds;
import lombok.Data;

@Data
public class StaffNeedsQueryVo extends StaffNeeds {
    private String realName;
    private String departmentName;
    private String positionName;
    private String reasonStr;
    private String genderStr;
    private String degreeStr;

    private String wedlockStr;
}
