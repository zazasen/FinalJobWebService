package com.cyrus.final_job.service;

import com.cyrus.final_job.entity.Resume;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.condition.InputResumeCondition;

import java.util.List;

/**
 * 简历表(Resume)表服务接口
 *
 * @author cyrus
 * @since 2020-04-01 14:51:57
 */
public interface ResumeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Resume queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Resume> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param resume 实例对象
     * @return 实例对象
     */
    Resume insert(Resume resume);

    /**
     * 修改数据
     *
     * @param resume 实例对象
     * @return 实例对象
     */
    Resume update(Resume resume);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 简历上传
     *
     * @param condition
     * @return
     */
    Result inputResume(InputResumeCondition condition);
}