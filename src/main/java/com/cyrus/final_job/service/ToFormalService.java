package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.ToFormal;
import com.cyrus.final_job.entity.base.Result;

import java.util.List;

/**
 * 转正表(ToFormal)表服务接口
 *
 * @author cyrus
 * @since 2020-04-07 14:37:11
 */
public interface ToFormalService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ToFormal queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ToFormal> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param toFormal 实例对象
     * @return 实例对象
     */
    ToFormal insert(ToFormal toFormal);

    /**
     * 修改数据
     *
     * @param toFormal 实例对象
     * @return 实例对象
     */
    ToFormal update(ToFormal toFormal);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 转正申请
     *
     * @param params
     * @return
     */
    Result conversionApply(JSONObject params);
}