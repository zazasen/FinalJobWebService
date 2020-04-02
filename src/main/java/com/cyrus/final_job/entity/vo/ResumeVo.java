package com.cyrus.final_job.entity.vo;

import com.cyrus.final_job.entity.Resume;
import lombok.Data;

@Data
public class ResumeVo extends Resume {
    private String departmentName;
    private String positionName;
    private String statusStr;
}
