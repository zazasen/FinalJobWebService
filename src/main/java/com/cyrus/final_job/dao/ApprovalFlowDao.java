package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.ApprovalFlow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 审批人员表(ApprovalFlow)表数据库访问层
 *
 * @author cyrus
 * @since 2020-02-26 21:04:05
 */
public interface ApprovalFlowDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ApprovalFlow queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ApprovalFlow> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param approvalFlow 实例对象
     * @return 对象列表
     */
    List<ApprovalFlow> queryAll(ApprovalFlow approvalFlow);

    /**
     * 新增数据
     *
     * @param approvalFlow 实例对象
     * @return 影响行数
     */
    int insert(ApprovalFlow approvalFlow);

    /**
     * 修改数据
     *
     * @param approvalFlow 实例对象
     * @return 影响行数
     */
    int update(ApprovalFlow approvalFlow);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    ApprovalFlow queryByDepId(Integer departmentId);
}