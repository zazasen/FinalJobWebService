package com.cyrus.final_job.entity.vo;

import com.cyrus.final_job.entity.RewardAndPunish;
import com.cyrus.final_job.entity.Salary;
import lombok.Data;

import java.util.List;

@Data
public class SalaryVo extends Salary {
    private String realName;
    private Integer workId;
    private Integer departmentId;

    private List<RewardAndPunish> rewardAndPunishes;

    private String departmentName;

    private String createTimeStr;
}
