package com.cyrus.final_job.entity.condition;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RemedySignCondition {
    private Integer userId;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer applyType;
}
