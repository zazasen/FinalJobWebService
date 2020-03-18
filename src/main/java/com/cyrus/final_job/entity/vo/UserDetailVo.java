package com.cyrus.final_job.entity.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDetailVo {

    /**
     * 主键ID
     */
    private Integer id;

    private String username;

    /**
     * 员工姓名
     */
    private String realName;
    /**
     * 性别,1 男 0 女
     */
    private Integer gender;

    private String genderStr;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Timestamp birthday;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 0 已婚 1 未婚 2 离异
     */
    private Integer wedlock;

    private String wedlockStr;

    /**
     * 民族
     */
    private Integer nationId;

    private String nationName;

    /**
     * 籍贯
     */
    private String nativePlace;
    /**
     * 政治面貌
     */
    private Integer politicsId;

    private String politicsStr;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 所属部门id
     */
    private Integer departmentId;

    private String departmentName;

    /**
     * 职位ID
     */
    private Integer positionId;

    private String positionName;

    /**
     * 职位级别
     */
    private String positionLevelName;

    /**
     * 最高学历,0 其他 1 小学 2 初中 3 高中 4 大专 5 本科 6 硕士 7 博士
     */
    private Integer topDegree;

    private String topDegreeStr;

    /**
     * 所属专业
     */
    private String specialty;
    /**
     * 毕业院校
     */
    private String school;
    /**
     * 在职状态:1 在职 0 离职
     */
    private Integer workState;

    private String workStateStr;

    /**
     * 工号
     */
    private Long workId;
    /**
     * 合同期限
     */
    private Integer contractTerm;

    private String contractTermStr;

    /**
     * 工龄
     */
    private Double workAge;

    private String workAgeStr;

    /**
     * 创建时间、入职时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;
    /**
     * 转正日期
     */
    private Timestamp conversionTime;
    /**
     * 离职日期
     */
    private Timestamp departureTime;
    /**
     * 合同起始日期
     */
    private Timestamp beginContractTime;
    /**
     * 合同终止日期
     */
    private Timestamp endContractTime;
    /**
     * 1 true，0 false
     */
    private Boolean enabled;

    private String enabledStr;
}
