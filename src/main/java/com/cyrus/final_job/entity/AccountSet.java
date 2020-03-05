package com.cyrus.final_job.entity;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 薪资账套表(AccountSet)实体类
 *
 * @author cyrus
 * @since 2020-03-05 14:10:16
 */
@Data
public class AccountSet {

    /**
     * 薪资账套表id
     */
    private Integer id;
    /**
     * 账套名
     */
    private String accountName;
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
     * 养老金基数
     */
    private Double pensionBasic;
    /**
     * 养老金比率
     */
    private Double pensionRatio;
    /**
     * 医疗保险金基数
     */
    private Double medicareBenefitsBasic;
    /**
     * 医疗保险金比率
     */
    private Double medicareBenefitsRatio;
    /**
     * 失业保险金基数
     */
    private Double businessInsuranceBasic;
    /**
     * 失业保险金比率
     */
    private Double businessInsuranceRatio;
    /**
     * 工伤保险金基数
     */
    private Double industrialInsuranceBasic;
    /**
     * 工伤保险金比率
     */
    private Double industrialInsuranceRatio;
    /**
     * 生育保险基数
     */
    private Double birthInsuranceBasic;
    /**
     * 生育保险比率
     */
    private Double birthInsuranceRatio;
    /**
     * 住房公积金基数
     */
    private Double housingFundBasic;
    /**
     * 住房公积金比率
     */
    private Double housingFundRatio;
    /**
     * 最终工资
     */
    private Double finalSalary;
    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 个人所得税
     */
    private Double taxes;

    public Result checkAndBuildParams() {
        if (accountName == null) {
            return Results.error("请输入账套名");
        }
        if (basicSalary == null) {
            return Results.error("请输入基本工资");
        }
        if (trafficAllowance == null) {
            return Results.error("请输入交通补助");
        }
        if (phoneAllowance == null) {
            return Results.error("请输入通信补助");
        }
        if (foodAllowance == null) {
            return Results.error("请输入餐饮补助");
        }
        if (pensionBasic == null) {
            return Results.error("请输入养老金基数");
        }
        if (pensionRatio == null) {
            return Results.error("请输入养老金比率");
        }
        if (medicareBenefitsBasic == null) {
            return Results.error("请输入医疗保险金基数");
        }
        if (medicareBenefitsRatio == null) {
            return Results.error("请输入医疗保险金比率");
        }
        if (businessInsuranceBasic == null) {
            return Results.error("请输入失业保险金基数");
        }
        if (businessInsuranceRatio == null) {
            return Results.error("请输入失业保险金比率");
        }
        if (industrialInsuranceBasic == null) {
            return Results.error("请输入工伤保险金基数");
        }
        if (industrialInsuranceRatio == null) {
            return Results.error("请输入工伤保险金比率");
        }
        if (birthInsuranceBasic == null) {
            return Results.error("请输入生育保险基数");
        }
        if (birthInsuranceRatio == null) {
            return Results.error("请输入生育保险金比率");
        }
        if (housingFundBasic == null) {
            return Results.error("请输入住房公积金基数");
        }
        if (housingFundRatio == null) {
            return Results.error("请输入住房公积金比率");
        }

        // 福利，不需要交税
        double noTaxes = trafficAllowance + phoneAllowance + foodAllowance;
        // 五险一金要交的钱
        double fiveAndOne = pensionBasic * pensionRatio
                + medicareBenefitsBasic * medicareBenefitsRatio
                + businessInsuranceBasic * businessInsuranceRatio
                + industrialInsuranceBasic * industrialInsuranceRatio
                + birthInsuranceBasic * birthInsuranceRatio
                + housingFundBasic * housingFundRatio;

        // 要交的税
        double taxes = calTaxes(basicSalary - fiveAndOne);
        this.taxes = taxes;
        this.finalSalary = basicSalary - fiveAndOne - taxes + noTaxes;
        this.createTime = DateUtils.getNowTime();
        return null;
    }


    private double calTaxes(double money) {
        double temp = 0.0;
        if (money > 960000) {
            temp += (money - 960000) * 0.45;
            money = money - (money - 960000);
        }
        if (money > 660000 && money <= 960000) {
            temp += (money - 660000) * 0.35;
            money = money - (money - 660000);
        }
        if (money > 420000 && money <= 660000) {
            temp += (money - 420000) * 0.3;
            money = money - (money - 420000);
        }
        if (money > 300000 && money <= 420000) {
            temp += (money - 300000) * 0.25;
            money = money - (money - 300000);
        }
        if (money > 144000 && money <= 300000) {
            temp += (money - 144000) * 0.2;
            money = money - (money - 144000);
        }
        if (money > 36000 && money <= 144000) {
            temp += (money - 36000) * 0.1;
            money = money - (money - 36000);
        }
        if (money <= 36000) {
            temp += money * 0.03;
            money = money - 36000;
        }
        return temp;
    }
}