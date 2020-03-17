package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.StaffNeeds;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 招聘需求表(StaffNeeds)表数据库访问层
 *
 * @author cyrus
 * @since 2020-03-17 17:07:51
 */
public interface StaffNeedsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    StaffNeeds queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<StaffNeeds> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param staffNeeds 实例对象
     * @return 对象列表
     */
    List<StaffNeeds> queryAll(StaffNeeds staffNeeds);

    /**
     * 新增数据
     *
     * @param staffNeeds 实例对象
     * @return 影响行数
     */
    int insert(StaffNeeds staffNeeds);

    /**
     * 修改数据
     *
     * @param staffNeeds 实例对象
     * @return 影响行数
     */
    int update(StaffNeeds staffNeeds);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}