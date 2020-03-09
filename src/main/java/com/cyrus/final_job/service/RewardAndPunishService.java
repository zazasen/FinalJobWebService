package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.RewardAndPunish;
import com.cyrus.final_job.entity.base.Result;

import java.util.List;

/**
 * 员工账套表(RewardAndPunish)表服务接口
 *
 * @author cyrus
 * @since 2020-03-09 19:41:42
 */
public interface RewardAndPunishService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RewardAndPunish queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<RewardAndPunish> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param rewardAndPunish 实例对象
     * @return 实例对象
     */
    RewardAndPunish insert(RewardAndPunish rewardAndPunish);

    /**
     * 修改数据
     *
     * @param rewardAndPunish 实例对象
     * @return 实例对象
     */
    RewardAndPunish update(RewardAndPunish rewardAndPunish);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    Result queryRewardAndPunish(JSONObject params);

    /**
     * 添加奖惩
     *
     * @param params
     * @return
     */
    Result addRewardAndPunish(JSONObject params);
}