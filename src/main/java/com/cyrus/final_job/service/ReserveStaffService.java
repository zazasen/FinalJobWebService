package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.ReserveStaff;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;

import java.util.List;

/**
 * 预备员工表(ReserveStaff)表服务接口
 *
 * @author cyrus
 * @since 2020-04-13 10:33:10
 */
public interface ReserveStaffService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ReserveStaff queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ReserveStaff> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param reserveStaff 实例对象
     * @return 实例对象
     */
    ReserveStaff insert(ReserveStaff reserveStaff);

    /**
     * 修改数据
     *
     * @param reserveStaff 实例对象
     * @return 实例对象
     */
    ReserveStaff update(ReserveStaff reserveStaff);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 获取所有预备员工
     *
     * @param params
     * @return
     */
    ResultPage getAllReserveStaff(JSONObject params);

    /**
     * 添加预备员工
     *
     * @param params
     * @return
     */
    Result addReserveStaff(JSONObject params);

    /**
     * 更新预备员工
     *
     * @param params
     * @return
     */
    Result updateReserveStaff(JSONObject params);

    /**
     * 删除单个预备员工
     *
     * @param params
     * @return
     */
    Result delReserveStaff(JSONObject params);

    /**
     * 删除多个预备员工
     *
     * @param params
     * @return
     */
    Result delReserveStaffs(JSONObject params);
}