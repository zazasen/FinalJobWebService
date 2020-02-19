package com.cyrus.final_job.entity.system.condition;

import com.cyrus.final_job.entity.base.BasePageQuery;
import lombok.Data;

@Data
public class RoleCondition extends BasePageQuery {

    private String roleName;
    /**
     * 角色中文名称
     */
    private String roleZh;
}
