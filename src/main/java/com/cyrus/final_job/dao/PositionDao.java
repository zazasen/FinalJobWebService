package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.Position;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 职位表(Position)表数据库访问层
 *
 * @author cyrus
 * @since 2020-02-19 21:03:21
 */
public interface PositionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Position queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Position> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<Position> queryByLimit(Position position);

    Long queryCount(Position position);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param position 实例对象
     * @return 对象列表
     */
    List<Position> queryAll(Position position);

    /**
     * 新增数据
     *
     * @param position 实例对象
     * @return 影响行数
     */
    int insert(Position position);

    /**
     * 修改数据
     *
     * @param position 实例对象
     * @return 影响行数
     */
    int update(Position position);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    void delByIds(@Param("ids") List<Integer> ids);
}