package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.ApprovalRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 审批人员表(ApprovalRecord)表数据库访问层
 *
 * @author cyrus
 * @since 2020-02-27 11:35:59
 */
public interface ApprovalRecordDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ApprovalRecord queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ApprovalRecord> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param approvalRecord 实例对象
     * @return 对象列表
     */
    List<ApprovalRecord> queryAll(ApprovalRecord approvalRecord);

    List<ApprovalRecord> queryAllPage(ApprovalRecord approvalRecord);

    Long queryAllPageCount(ApprovalRecord approvalRecord);

    /**
     * 新增数据
     *
     * @param approvalRecord 实例对象
     * @return 影响行数
     */
    int insert(ApprovalRecord approvalRecord);

    /**
     * 修改数据
     *
     * @param approvalRecord 实例对象
     * @return 影响行数
     */
    int update(ApprovalRecord approvalRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    void delByApprovalTypeAndApprovalId(@Param("code") Integer code, @Param("id") Integer id);
}