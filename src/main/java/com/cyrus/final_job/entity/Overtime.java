package com.cyrus.final_job.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 员工加班申请(Overtime)实体类
 *
 * @author cyrus
 * @since 2020-03-04 14:57:11
 */
@Data
public class Overtime{
    /**
    * 加班表id
    */
    private Integer id;
    /**
    * user表id
    */
    private Integer userId;
    /**
    * 加班开始日期
    */
    private Timestamp startTime;
    /**
    * 加班结束日期
    */
    private Timestamp endTime;
    /**
    * 加班持续天数
    */
    private Integer continueDay;
    /**
    * 加班原因
    */
    private String reason;
    /**
    * 当前记录状态:0 不可用 1 可用
    */
    private Integer enabled;
    /**
    * 记录创建时间
    */
    private Timestamp createTime;

}