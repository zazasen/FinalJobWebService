package com.cyrus.final_job.entity.condition;

import com.cyrus.final_job.entity.base.BasePageQuery;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserCondition extends BasePageQuery {

    /**
     * 员工姓名
     */
    private String realName;

    /**
     * 性别,1 男 0 女
     */
    private Integer gender;

    /**
     * 0 已婚 1 未婚 2 离异
     */
    private Integer wedlock;

    /**
     * 民族
     */
    private Integer nationId;


    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 政治面貌
     */
    private Integer politicsId;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 所属部门id
     */
    private Integer departmentId;

    /**
     * 职位ID
     */
    private Integer positionId;

    /**
     * 最高学历,0 其他 1 小学 2 初中 3 高中 4 大专 5 本科 6 硕士 7 博士
     */
    private Integer topDegree;

    /**
     * 在职状态:1 在职 0 离职
     */
    private Integer workState;

    /**
     * 工号
     */
    private Long workId;

    /**
     * 创建时间、入职时间
     */
    private Timestamp createTimeStart;

    private Timestamp createTimeEnd;

    /**
     * 离职日期
     */
    private Timestamp departureTimeStart;

    private Timestamp departureTimeEnd;
}
