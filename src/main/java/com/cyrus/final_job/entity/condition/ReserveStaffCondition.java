package com.cyrus.final_job.entity.condition;

import com.cyrus.final_job.entity.base.BasePageQuery;
import lombok.Data;

@Data
public class ReserveStaffCondition extends BasePageQuery {
    private String name;
    private Integer departmentId;
    private Integer status;
}
