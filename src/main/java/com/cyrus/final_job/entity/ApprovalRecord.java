package com.cyrus.final_job.entity;

import com.cyrus.final_job.entity.base.BasePageQuery;
import lombok.Data;

/**
 * 审批人员表(ApprovalRecord)实体类
 *
 * @author cyrus
 * @since 2020-02-27 11:35:58
 */
@Data
public class ApprovalRecord extends BasePageQuery {
    /**
    * 审批记录
    */
    private Integer id;
    /**
    * 请求发起人
    */
    private Integer produceUserId;
    /**
    * 审批类型 0 补卡 1 请假 2 周末加班
    */
    private Integer approvalType;
    /**
    * 审批人
    */
    private Integer approvalUserId;
    /**
    * 记录状态 0 待审批 1 已审批
    */
    private Integer recordStatus;
    /**
    * 对应审批类型表的id，比如补卡对应签到表id
    */
    private Integer approvalId;



}