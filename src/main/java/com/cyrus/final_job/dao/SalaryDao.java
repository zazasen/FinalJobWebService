package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.Salary;
import com.cyrus.final_job.entity.condition.SalaryCondition;
import com.cyrus.final_job.entity.condition.TimeRangeCondition;
import com.cyrus.final_job.entity.vo.SalaryVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工账套表(Salary)表数据库访问层
 *
 * @author cyrus
 * @since 2020-03-10 13:51:02
 */
public interface SalaryDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Salary queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Salary> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param salary 实例对象
     * @return 对象列表
     */
    List<Salary> queryAll(Salary salary);

    /**
     * 查新薪资数据，带员工数据
     *
     * @param condition
     * @return
     */
    List<SalaryVo> queryWithUserByCondition(SalaryCondition condition);

    Long queryWithUserByConditionCount(SalaryCondition condition);

    /**
     * 新增数据
     *
     * @param salary 实例对象
     * @return 影响行数
     */
    int insert(Salary salary);

    /**
     * 修改数据
     *
     * @param salary 实例对象
     * @return 影响行数
     */
    int update(Salary salary);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    Salary querySalaryByUserIdAndTime(@Param("userId") Integer userId, @Param("createTime") LocalDate createTime);

    /**
     * 获取个人薪资
     *
     * @param condition
     * @return
     */
    List<Salary> queryOneByCondition(SalaryCondition condition);

    Long queryOneByConditionCount(SalaryCondition condition);

    /**
     * 薪资统计查询
     * @param userIds
     * @param timeRange
     * @return
     */
    List<Salary> queryByUserIds(@Param("userIds") List<Integer> userIds, @Param("timeRange") TimeRangeCondition timeRange);
}