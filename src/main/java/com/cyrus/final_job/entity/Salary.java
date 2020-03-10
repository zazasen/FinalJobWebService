package com.cyrus.final_job.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 员工账套表(Salary)实体类
 *
 * @author cyrus
 * @since 2020-03-10 13:51:02
 */
@Data
public class Salary {

    /**
     * 薪资表id
     */
    private Integer id;
    /**
     * user表id
     */
    private Integer userId;
    /**
     * 基本工资
     */
    private Double basicSalary;
    /**
     * 交通补助
     */
    private Double trafficAllowance;
    /**
     * 通讯补助
     */
    private Double phoneAllowance;
    /**
     * 餐饮补助
     */
    private Double foodAllowance;
    /**
     * 个人所得税
     */
    private Double taxes;
    /**
     * 五险一金费用
     */
    private Double fiveAndOne;
    /**
     * 考勤异常扣除
     */
    private Double checkInMoney;
    /**
     * 考勤异常扣除说明
     */
    private String checkInReason;
    /**
     * 奖惩钱
     */
    private Double rewardAndPunishMoney;
    /**
     * 其他薪资项
     */
    private Double otherMoney;
    /**
     * 其他薪资项说明
     */
    private String otherMoneyReason;
    /**
     * 实发工资
     */
    private Double finalSalary;
    /**
     * 创建时间
     */
    private Timestamp createTime;

}