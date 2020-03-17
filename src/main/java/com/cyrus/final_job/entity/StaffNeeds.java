package com.cyrus.final_job.entity;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.utils.Results;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 招聘需求表(StaffNeeds)实体类
 *
 * @author cyrus
 * @since 2020-03-17 17:07:51
 */
@Data
public class StaffNeeds {

    /**
     * 招聘需求表id
     */
    private Integer id;
    /**
     * 申请人
     */
    private Integer userId;
    /**
     * 申请部门id
     */
    private Integer departmentId;
    /**
     * 申请时间
     */
    private Timestamp applyDate;
    /**
     * 岗位地址
     */
    private String address;
    /**
     * 申请职位
     */
    private Integer positionId;
    /**
     * 到岗日期
     */
    private Timestamp entryDate;
    /**
     * 招聘人数
     */
    private Integer needNumber;
    /**
     * 1 职称补充 2 扩大编制 3 招聘人数 4 短期需求 5 储备人才
     */
    private Integer reason;
    /**
     * 岗位职责
     */
    private String responsibility;
    /**
     * 最低学历
     */
    private Integer degree;
    /**
     * 年龄上限
     */
    private Integer highestAge;
    /**
     * 年龄下限
     */
    private Integer minimumAge;
    /**
     * 婚姻状况
     */
    private Integer wedlock;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 专业限制
     */
    private String speciality;
    /**
     * 外语要求
     */
    private String foreignLanguages;
    /**
     * 技能
     */
    private String skill;

    public Result checkParams() {

        if (Objects.isNull(this.departmentId)) {
            return Results.error("请选择部门");
        }
        if (Objects.isNull(this.applyDate)) {
            return Results.error("请选择申请时间");
        }
        if (Objects.isNull(this.address)) {
            return Results.error("请输入岗位地址");
        }
        if (Objects.isNull(this.positionId)) {
            return Results.error("请选择招聘岗位");
        }
        if (Objects.isNull(this.entryDate)) {
            return Results.error("请选择到岗日期");
        }
        if (Objects.isNull(this.reason)) {
            return Results.error("请选择招聘理由");
        }
        if (Objects.isNull(this.responsibility)) {
            return Results.error("请输入岗位职责");
        }
        if (Objects.isNull(this.degree)) {
            return Results.error("请选择最低学历");
        }
        return null;
    }
}