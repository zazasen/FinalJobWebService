package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.Overtime;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工加班申请(Overtime)表数据库访问层
 *
 * @author cyrus
 * @since 2020-03-04 14:57:12
 */
public interface OvertimeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Overtime queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Overtime> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param overtime 实例对象
     * @return 对象列表
     */
    List<Overtime> queryAll(Overtime overtime);

    /**
     * 新增数据
     *
     * @param overtime 实例对象
     * @return 影响行数
     */
    int insert(Overtime overtime);

    /**
     * 修改数据
     *
     * @param overtime 实例对象
     * @return 影响行数
     */
    int update(Overtime overtime);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}