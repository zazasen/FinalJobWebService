package com.cyrus.final_job.entity.condition;

import com.cyrus.final_job.entity.base.BasePageQuery;
import lombok.Data;

@Data
public class UserAccountSetQueryCondition extends BasePageQuery {
    private String realName;
    private Long workId;
    private Integer departmentId;

    private Integer userId;
}
