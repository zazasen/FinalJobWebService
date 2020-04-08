package com.cyrus.final_job.entity;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.utils.Results;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 员工账套表(RewardAndPunish)实体类
 *
 * @author cyrus
 * @since 2020-03-09 19:41:41
 */
@Data
public class RewardAndPunish {

    /**
     * 奖惩表id
     */
    private Integer id;
    /**
     * user表id，奖惩对象
     */
    private Integer userId;
    /**
     * 奖惩金额，正数代表奖励，负数代表惩罚
     */
    private Double money;
    /**
     * 奖惩原因
     */
    private String reason;
    /**
     * 创建时间
     */
    private Timestamp createTime;

    public Result checkParams() {
        if (Objects.isNull(userId)) return Results.error("userId 不能为空");
        if (Objects.isNull(money)) return Results.error("奖惩金额不能为空");
        if (StringUtils.isEmpty(reason)) return Results.error("奖惩原因不能为空");
        if (Objects.isNull(createTime)) return Results.error("奖惩时间不能为空");
        return null;
    }
}