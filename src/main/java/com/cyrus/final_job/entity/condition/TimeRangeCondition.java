package com.cyrus.final_job.entity.condition;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TimeRangeCondition {
    private Timestamp startTime;
    private Timestamp endTime;
}
