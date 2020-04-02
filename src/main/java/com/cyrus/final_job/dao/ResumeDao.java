package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.Resume;
import com.cyrus.final_job.entity.condition.ResumeQueryCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 简历表(Resume)表数据库访问层
 *
 * @author cyrus
 * @since 2020-04-01 14:51:56
 */
public interface ResumeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Resume queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Resume> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 分页条件查询简历
     *
     * @param condition
     * @return
     */
    List<Resume> queryPageByCondition(ResumeQueryCondition condition);

    Long queryPageByConditionCount(ResumeQueryCondition condition);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param resume 实例对象
     * @return 对象列表
     */
    List<Resume> queryAll(Resume resume);

    /**
     * 新增数据
     *
     * @param resume 实例对象
     * @return 影响行数
     */
    int insert(Resume resume);

    /**
     * 修改数据
     *
     * @param resume 实例对象
     * @return 影响行数
     */
    int update(Resume resume);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);
}