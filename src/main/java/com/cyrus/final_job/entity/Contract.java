package com.cyrus.final_job.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 员工合同表(Contract)实体类
 *
 * @author cyrus
 * @since 2020-03-12 15:22:34
 */
@Data
public class Contract {

    /**
    * 合同表id
    */
    private Integer id;
    /**
    * user表id
    */
    private Integer userId;
    /**
    * 合同开始日期
    */
    private Timestamp beginTime;
    /**
    * 合同结束日期
    */
    private Timestamp endTime;
    /**
    * 合同详情
    */
    private String content;
    /**
    * 员工是否已经确认了该合同
    */
    private Integer confirm;
}