package com.cyrus.final_job.entity.condition;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.utils.Results;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Objects;

@Data
public class SalaryEditCondition {
    private Double money;
    private String reason;
    private Integer userId;
    private Timestamp createTime;

    public Result checkParams() {
        if (Objects.isNull(money)) return Results.error("差价金额不能为空");
        if (StringUtils.isEmpty(reason)) return Results.error("差价原因不能为空");
        if (Objects.isNull(userId)) return Results.error("userId 不能为空");
        if (Objects.isNull(createTime)) return Results.error("createTime 不能为空");
        return null;
    }
}
