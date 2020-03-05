package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.Overtime;
import com.cyrus.final_job.entity.base.Result;

import java.util.List;

/**
 * 员工加班申请(Overtime)表服务接口
 *
 * @author cyrus
 * @since 2020-03-04 14:57:12
 */
public interface OvertimeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Overtime queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Overtime> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param overtime 实例对象
     * @return 实例对象
     */
    Overtime insert(Overtime overtime);

    /**
     * 修改数据
     *
     * @param overtime 实例对象
     * @return 实例对象
     */
    Overtime update(Overtime overtime);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 加班申请
     * @param params
     * @return
     */
    Result overtimeApply(JSONObject params);
}