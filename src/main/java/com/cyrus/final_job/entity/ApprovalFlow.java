package com.cyrus.final_job.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 审批人员表(ApprovalFlow)实体类
 *
 * @author cyrus
 * @since 2020-02-26 21:04:04
 */

@Data
public class ApprovalFlow implements Serializable {
    private static final long serialVersionUID = -51504196546385526L;
    /**
    * 签到表id
    */
    private Integer id;
    /**
    * 部门id
    */
    private Integer departmentId;
    /**
    * 审批流类型：0 补签 2 请假
    */
    private Integer flowType;
    /**
    * 第一审批人user_id
    */
    private Integer firstApprovalMan;
    /**
    * 第二审批人user_id
    */
    private Integer secondApprovalMan;
    /**
    * 第三审批人user_id
    */
    private Integer thirdApprovalMan;

}