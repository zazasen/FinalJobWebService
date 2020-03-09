package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.QuitJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 离职表(QuitJob)表数据库访问层
 *
 * @author cyrus
 * @since 2020-03-09 09:52:06
 */
public interface QuitJobDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QuitJob queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<QuitJob> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param quitJob 实例对象
     * @return 对象列表
     */
    List<QuitJob> queryAll(QuitJob quitJob);

    /**
     * 新增数据
     *
     * @param quitJob 实例对象
     * @return 影响行数
     */
    int insert(QuitJob quitJob);

    /**
     * 修改数据
     *
     * @param quitJob 实例对象
     * @return 影响行数
     */
    int update(QuitJob quitJob);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}