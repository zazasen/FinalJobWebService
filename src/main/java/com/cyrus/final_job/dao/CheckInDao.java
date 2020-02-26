package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.CheckIn;
import com.cyrus.final_job.entity.condition.CheckInCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 签到表(CheckIn)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-25 13:06:40
 */
public interface CheckInDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CheckIn queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CheckIn> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    List<CheckIn> queryAllByCondition(CheckInCondition condition);

    Long queryAllByConditionCount(CheckInCondition condition);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param checkIn 实例对象
     * @return 对象列表
     */
    List<CheckIn> queryAll(CheckIn checkIn);

    /**
     * 新增数据
     *
     * @param checkIn 实例对象
     * @return 影响行数
     */
    int insert(CheckIn checkIn);

    /**
     * 修改数据
     *
     * @param checkIn 实例对象
     * @return 影响行数
     */
    int update(CheckIn checkIn);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    CheckIn queryByIdAndCreateTime(CheckIn checkIn);

    CheckIn queryByCreateTime(String createTime);
}