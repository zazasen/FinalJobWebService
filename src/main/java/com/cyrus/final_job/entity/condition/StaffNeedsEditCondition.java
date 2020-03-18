package com.cyrus.final_job.entity.condition;

import lombok.Data;

@Data
public class StaffNeedsEditCondition {
    private Integer id;
    private Double salaryTop;
    private Double salaryLow;
    private Integer publish;
}
