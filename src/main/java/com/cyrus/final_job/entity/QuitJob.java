package com.cyrus.final_job.entity;

import lombok.Data;

import java.sql.Timestamp;

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
    private Integer enabled;
    /**
     * 离职时间
     */
    private Timestamp createTime;

}