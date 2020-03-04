package com.cyrus.final_job.entity.condition;

import com.cyrus.final_job.entity.base.BasePageQuery;
import lombok.Data;

@Data
public class LeaveCondition extends BasePageQuery {
    private Integer holidayType;
    private Integer userId;
}
