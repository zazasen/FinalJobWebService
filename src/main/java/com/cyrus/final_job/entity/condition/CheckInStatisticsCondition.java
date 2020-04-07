package com.cyrus.final_job.entity.condition;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CheckInStatisticsCondition {
    private Integer departmentId;
    private Timestamp date;
    private String createTime;
}
