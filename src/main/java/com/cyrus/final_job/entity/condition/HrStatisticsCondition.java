package com.cyrus.final_job.entity.condition;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class HrStatisticsCondition {
    private Integer departmentId;
    private Timestamp date;

    private Timestamp begin;

    private Timestamp end;
}
