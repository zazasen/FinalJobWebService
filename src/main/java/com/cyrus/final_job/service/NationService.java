package com.cyrus.final_job.service;

import com.cyrus.final_job.entity.Nation;
import com.cyrus.final_job.entity.base.Result;

import java.util.List;

/**
 * 民族表(Nation)表服务接口
 *
 * @author cyrus
 * @since 2020-02-20 15:32:38
 */
public interface NationService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Nation queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Nation> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nation 实例对象
     * @return 实例对象
     */
    Nation insert(Nation nation);

    /**
     * 修改数据
     *
     * @param nation 实例对象
     * @return 实例对象
     */
    Nation update(Nation nation);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 获取民族下拉列表
     *
     * @return res
     */
    Result getNations();
}