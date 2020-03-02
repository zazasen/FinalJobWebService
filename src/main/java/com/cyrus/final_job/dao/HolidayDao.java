package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.Holiday;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工假期表(Holiday)表数据库访问层
 *
 * @author cyrus
 * @since 2020-03-01 10:36:26
 */
public interface HolidayDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Holiday queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Holiday> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param holiday 实例对象
     * @return 对象列表
     */
    List<Holiday> queryAll(Holiday holiday);


    Holiday queryByUserIdAndTypeInCurrentYear(Holiday holiday);


    /**
     * 新增数据
     *
     * @param holiday 实例对象
     * @return 影响行数
     */
    int insert(Holiday holiday);

    /**
     * 修改数据
     *
     * @param holiday 实例对象
     * @return 影响行数
     */
    int update(Holiday holiday);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    int deleteByUserId(Integer userId);
}