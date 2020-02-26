package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.ApprovalFlow;
import com.cyrus.final_job.entity.base.Result;

import java.util.List;

/**
 * 审批人员表(ApprovalFlow)表服务接口
 *
 * @author cyrus
 * @since 2020-02-26 21:04:06
 */
public interface ApprovalFlowService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ApprovalFlow queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ApprovalFlow> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param approvalFlow 实例对象
     * @return 实例对象
     */
    ApprovalFlow insert(ApprovalFlow approvalFlow);

    /**
     * 修改数据
     *
     * @param approvalFlow 实例对象
     * @return 实例对象
     */
    ApprovalFlow update(ApprovalFlow approvalFlow);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 更新审批流
     *
     * @param params
     * @return
     */
    Result changeApprovalFlow(JSONObject params);
}