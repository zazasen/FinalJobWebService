package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.Leave;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工请假表(Leave)表数据库访问层
 *
 * @author cyrus
 * @since 2020-03-02 14:58:09
 */
public interface LeaveDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Leave queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Leave> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param leave 实例对象
     * @return 对象列表
     */
    List<Leave> queryAll(Leave leave);

    /**
     * 新增数据
     *
     * @param leave 实例对象
     * @return 影响行数
     */
    int insert(Leave leave);

    /**
     * 修改数据
     *
     * @param leave 实例对象
     * @return 影响行数
     */
    int update(Leave leave);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}