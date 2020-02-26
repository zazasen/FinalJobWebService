package com.cyrus.final_job.entity.condition;

import com.cyrus.final_job.entity.base.BasePageQuery;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CheckInCondition extends BasePageQuery {
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer userId;
}
