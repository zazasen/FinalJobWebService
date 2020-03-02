package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.Holiday;
import com.cyrus.final_job.entity.base.Result;

import java.util.List;

/**
 * 员工假期表(Holiday)表服务接口
 *
 * @author cyrus
 * @since 2020-03-01 10:36:26
 */
public interface HolidayService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Holiday queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Holiday> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param holiday 实例对象
     * @return 实例对象
     */
    Holiday insert(Holiday holiday);

    /**
     * 修改数据
     *
     * @param holiday 实例对象
     * @return 实例对象
     */
    Holiday update(Holiday holiday);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 获取假期
     *
     * @return
     */
    Result getMyHolidays(JSONObject params);
}