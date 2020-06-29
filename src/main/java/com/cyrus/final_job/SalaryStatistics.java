package com.cyrus.final_job;

import lombok.Data;

@Data
public class SalaryStatistics {

    private String departName;

    private Integer departmentId;

    // 交通补助统计
    private Double trafficAllowanceStatistics;
    // 通讯补助统计
    private Double phoneAllowanceStatistics;
    // 餐饮补助统计
    private Double foodAllowanceStatistics;
    // 五险一金统计
    private Double fiveAndOneStatistics;
    // 奖惩统计
    private Double rewardAndPunishMoneyStatistics;
    // 其他薪资项统计
    private Double otherMoneyStatistics;
    // 实发工资统计
    private Double finalSalaryStatistics;
}
