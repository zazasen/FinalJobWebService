package com.cyrus.final_job.entity.condition;

import com.cyrus.final_job.entity.base.BasePageQuery;
import lombok.Data;

@Data
public class ResumeQueryCondition extends BasePageQuery {
    private Integer positionId;
    private Integer departmentId;
    private Integer status;
}
