package com.cyrus.final_job.entity.condition;

import com.cyrus.final_job.entity.base.BasePageQuery;
import lombok.Data;

@Data
public class StaffNeedsQueryCondition extends BasePageQuery {
    private Integer departmentId;

    private Integer publish;
}
