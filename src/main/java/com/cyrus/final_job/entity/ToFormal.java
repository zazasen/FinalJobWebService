package com.cyrus.final_job.entity;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

/**
 * 转正表(ToFormal)实体类
 *
 * @author cyrus
 * @since 2020-04-07 14:35:53
 */
@Data
public class ToFormal {

    /**
     * 转正表id
     */
    private Integer id;
    /**
     * user表id
     */
    private Integer userId;
    /**
     * 转正原因
     */
    private String reason;
    /**
     * 当前记录状态:0 不可用 1 可用
     */
    private Boolean enabled;
    /**
     * 创建时间
     */
    private Timestamp createTime;

    public Result checkAndBuildParams() {
        if (StringUtils.isEmpty(reason)) return Results.error("转正原因不能为空");
        this.userId = UserUtils.getCurrentUserId();
        this.enabled = false;
        return null;
    }
}