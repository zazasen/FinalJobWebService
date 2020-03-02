package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.ApprovalFlowDao;
import com.cyrus.final_job.entity.ApprovalFlow;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.ApprovalFlowService;
import com.cyrus.final_job.utils.Results;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 审批人员表(ApprovalFlow)表服务实现类
 *
 * @author cyrus
 * @since 2020-02-26 21:04:06
 */
@Service("approvalFlowService")
public class ApprovalFlowServiceImpl implements ApprovalFlowService {
    @Resource
    private ApprovalFlowDao approvalFlowDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ApprovalFlow queryById(Integer id) {
        return this.approvalFlowDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ApprovalFlow> queryAllByLimit(int offset, int limit) {
        return this.approvalFlowDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param approvalFlow 实例对象
     * @return 实例对象
     */
    @Override
    public ApprovalFlow insert(ApprovalFlow approvalFlow) {
        this.approvalFlowDao.insert(approvalFlow);
        return approvalFlow;
    }

    /**
     * 修改数据
     *
     * @param approvalFlow 实例对象
     * @return 实例对象
     */
    @Override
    public ApprovalFlow update(ApprovalFlow approvalFlow) {
        this.approvalFlowDao.update(approvalFlow);
        return this.queryById(approvalFlow.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.approvalFlowDao.deleteById(id) > 0;
    }

    @Override
    public Result changeApprovalFlow(JSONObject params) {
        ApprovalFlow approvalFlow = params.toJavaObject(ApprovalFlow.class);
        if (approvalFlow.getDepartmentId() == null) {
            return Results.error("部门id不能为空");
        }
        Integer first = approvalFlow.getFirstApprovalMan();
        Integer second = approvalFlow.getSecondApprovalMan();
        Integer third = approvalFlow.getThirdApprovalMan();

        if (first == null && (second != null || third != null)) {
            return Results.error("不能在未指派第一审批人的情况下指派第二第三审批人");
        }
        if (second != null && third == null) {
            return Results.error("拥有第一第二审批人的情况下必须拥有第三审批人");
        }

        if (first != null && second != null) {
            if (first == second) {
                return Results.error("审批人不能相同");
            }
        }
        if (first != null && third != null) {
            if (first == third) {
                return Results.error("审批人不能相同");
            }
        }
        if (second != null && third != null) {
            if (second == third) {
                return Results.error("审批人不能相同");
            }
        }

        ApprovalFlow flow = approvalFlowDao.queryByDepId(approvalFlow.getDepartmentId());
        if (flow == null) {
            approvalFlowDao.insert(approvalFlow);
        } else {
            approvalFlow.setId(flow.getId());
            approvalFlowDao.update(approvalFlow);
        }
        return Results.createOk("更新成功");
    }
}