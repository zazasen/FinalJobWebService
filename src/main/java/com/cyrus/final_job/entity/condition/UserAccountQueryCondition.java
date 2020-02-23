package com.cyrus.final_job.entity.condition;

import com.cyrus.final_job.entity.base.BasePageQuery;
import lombok.Data;

@Data
public class UserAccountQueryCondition extends BasePageQuery {
    private String realName;
    private String username;
    private Long workId;
}
