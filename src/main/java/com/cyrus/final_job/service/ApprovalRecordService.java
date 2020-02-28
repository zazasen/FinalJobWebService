package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.ApprovalRecord;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;

import java.util.List;

/**
 * 审批人员表(ApprovalRecord)表服务接口
 *
 * @author cyrus
 * @since 2020-02-27 11:35:59
 */
public interface ApprovalRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ApprovalRecord queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ApprovalRecord> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param approvalRecord 实例对象
     * @return 实例对象
     */
    ApprovalRecord insert(ApprovalRecord approvalRecord);

    /**
     * 修改数据
     *
     * @param approvalRecord 实例对象
     * @return 实例对象
     */
    ApprovalRecord update(ApprovalRecord approvalRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 获取当前登录用户的所有审批记录
     *
     * @return
     */
    ResultPage getAllMyApproval(JSONObject params);

    /**
     * 获取具体某一条记录的详情
     *
     * @param params
     * @return
     */
    Result getApprovalDetail(JSONObject params);

    /**
     * 审批通过处理
     *
     * @param params
     * @return
     */
    Result approvalPass(JSONObject params);

    /**
     * 审批驳回
     *
     * @param params
     * @return
     */
    Result notPass(JSONObject params);
}