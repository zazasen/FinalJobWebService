package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.ToFormal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 转正表(ToFormal)表数据库访问层
 *
 * @author cyrus
 * @since 2020-04-07 14:37:10
 */
public interface ToFormalDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ToFormal queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ToFormal> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param toFormal 实例对象
     * @return 对象列表
     */
    List<ToFormal> queryAll(ToFormal toFormal);

    /**
     * 新增数据
     *
     * @param toFormal 实例对象
     * @return 影响行数
     */
    int insert(ToFormal toFormal);

    /**
     * 修改数据
     *
     * @param toFormal 实例对象
     * @return 影响行数
     */
    int update(ToFormal toFormal);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}