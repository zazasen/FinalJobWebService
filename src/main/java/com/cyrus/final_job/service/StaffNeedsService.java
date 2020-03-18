package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.StaffNeeds;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;

import java.util.List;

/**
 * 招聘需求表(StaffNeeds)表服务接口
 *
 * @author cyrus
 * @since 2020-03-17 17:07:52
 */
public interface StaffNeedsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    StaffNeeds queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<StaffNeeds> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param staffNeeds 实例对象
     * @return 实例对象
     */
    StaffNeeds insert(StaffNeeds staffNeeds);

    /**
     * 修改数据
     *
     * @param staffNeeds 实例对象
     * @return 实例对象
     */
    StaffNeeds update(StaffNeeds staffNeeds);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 提交招聘需求
     *
     * @param params
     * @return
     */
    Result submitStaffNeed(JSONObject params);

    /**
     * 获取招聘看板数据
     *
     * @param params
     * @return
     */
    ResultPage getStaffNeedsDate(JSONObject params);

    /**
     * 获取招聘详情
     *
     * @param params
     * @return
     */
    Result getStaffNeedsDetail(JSONObject params);


    Result editStaffNeeds(JSONObject params);

    ResultPage getPublishedStaffNeeds(JSONObject params);
}