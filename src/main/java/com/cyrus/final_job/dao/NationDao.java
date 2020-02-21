package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.Nation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 民族表(Nation)表数据库访问层
 *
 * @author cyrus
 * @since 2020-02-20 15:32:37
 */
public interface NationDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Nation queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Nation> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nation 实例对象
     * @return 对象列表
     */
    List<Nation> queryAll(Nation nation);

    /**
     * 新增数据
     *
     * @param nation 实例对象
     * @return 影响行数
     */
    int insert(Nation nation);

    /**
     * 修改数据
     *
     * @param nation 实例对象
     * @return 影响行数
     */
    int update(Nation nation);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}