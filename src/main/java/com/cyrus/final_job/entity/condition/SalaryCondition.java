package com.cyrus.final_job.entity.condition;

import com.cyrus.final_job.entity.base.BasePageQuery;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class SalaryCondition extends BasePageQuery {
    private String realName;
    private Integer workId;
    private String departmentId;
    private Timestamp month;
    private Integer workState;
}
