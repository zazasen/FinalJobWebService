package com.cyrus.final_job.entity;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 离职表(QuitJob)实体类
 *
 * @author cyrus
 * @since 2020-03-09 09:52:05
 */

@Data
public class QuitJob {

    /**
     * 离职表id
     */
    private Integer id;
    /**
     * user表id
     */
    private Integer userId;
    /**
     * 离职原因
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

    /**
     * 离职时间
     */
    private Timestamp leaveTime;


    public Result checkAndBuildParams() {
        if (leaveTime == null) return Results.error("离职时间不能为空");
        if (reason == null) return Results.error("离职原因不能为空");
        if (leaveTime.toLocalDateTime().isBefore(LocalDateTime.now())) return Results.error("请选择正确的离职时间");
        this.userId = UserUtils.getCurrentUserId();
        this.enabled = false;
        this.createTime = DateUtils.getNowTime();
        return null;
    }
}