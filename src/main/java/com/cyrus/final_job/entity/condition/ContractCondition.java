package com.cyrus.final_job.entity.condition;

import com.cyrus.final_job.entity.base.BasePageQuery;
import lombok.Data;

@Data
public class ContractCondition extends BasePageQuery {

    private String realName;
    private Integer workId;
    private Integer departmentId;
    private Integer workState;

}
