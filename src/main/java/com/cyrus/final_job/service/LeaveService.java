package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.Leave;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;

import java.util.List;

/**
 * 员工请假表(Leave)表服务接口
 *
 * @author cyrus
 * @since 2020-03-02 14:58:09
 */
public interface LeaveService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Leave queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Leave> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param leave 实例对象
     * @return 实例对象
     */
    Leave insert(Leave leave);

    /**
     * 修改数据
     *
     * @param leave 实例对象
     * @return 实例对象
     */
    Leave update(Leave leave);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 请假申请
     *
     * @param params
     * @return
     */
    Result leaveApply(JSONObject params);

    /**
     * 获取目前已经申请的假期
     *
     * @return
     */
    ResultPage getMyAppliedHolidays(JSONObject params);
}