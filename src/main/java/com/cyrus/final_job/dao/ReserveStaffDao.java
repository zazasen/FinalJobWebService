package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.ReserveStaff;
import com.cyrus.final_job.entity.condition.ReserveStaffCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预备员工表(ReserveStaff)表数据库访问层
 *
 * @author cyrus
 * @since 2020-04-13 10:33:10
 */
public interface ReserveStaffDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ReserveStaff queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ReserveStaff> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<ReserveStaff> getAllReserveStaff(ReserveStaffCondition condition);

    Long getAllReserveStaffCount(ReserveStaffCondition condition);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param reserveStaff 实例对象
     * @return 对象列表
     */
    List<ReserveStaff> queryAll(ReserveStaff reserveStaff);

    /**
     * 新增数据
     *
     * @param reserveStaff 实例对象
     * @return 影响行数
     */
    int insert(ReserveStaff reserveStaff);

    /**
     * 修改数据
     *
     * @param reserveStaff 实例对象
     * @return 影响行数
     */
    int update(ReserveStaff reserveStaff);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);
}