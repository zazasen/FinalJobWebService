package com.cyrus.final_job.entity;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.enums.EnableBooleanEnum;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 员工请假表(Leave)实体类
 *
 * @author cyrus
 * @since 2020-03-02 14:58:08
 */
@Data
public class Leave {

    /**
     * 请假表id
     */
    private Integer id;
    /**
     * user表id
     */
    private Integer userId;
    /**
     * 假期类型 0 调休 1 带薪病假 2 年假 3 哺乳假 4 婚假 5 丧假
     */
    private Integer holidayType;
    /**
     * 假期开始时间
     */
    private Timestamp beginTime;
    /**
     * 假期结束时间
     */
    private Timestamp endTime;
    /**
     * 请假原因
     */
    private String reason;
    /**
     * 当前记录状态:0 不可用 1 可用
     */
    private Boolean enabled;
    /**
     * 记录创建时间
     */
    private Timestamp createTime;

    public Result checkAndBuildParams() {
        if (holidayType == null) return Results.error("假期类型不能为空");
        if (beginTime == null) return Results.error("假期开始时间不能为空");
        if (endTime == null) return Results.error("假期结束时间不能为空");
        if (reason == null) return Results.error("请假原因不能为空");
        this.userId = UserUtils.getCurrentUserId();
        this.enabled = EnableBooleanEnum.DISABLE.getCode();
        this.createTime = DateUtils.getNowTime();
        return null;
    }

}