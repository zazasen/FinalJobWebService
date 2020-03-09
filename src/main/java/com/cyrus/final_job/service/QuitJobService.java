package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.QuitJob;
import com.cyrus.final_job.entity.base.Result;

import java.util.List;

/**
 * 离职表(QuitJob)表服务接口
 *
 * @author cyrus
 * @since 2020-03-09 09:52:06
 */
public interface QuitJobService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QuitJob queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<QuitJob> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param quitJob 实例对象
     * @return 实例对象
     */
    QuitJob insert(QuitJob quitJob);

    /**
     * 修改数据
     *
     * @param quitJob 实例对象
     * @return 实例对象
     */
    QuitJob update(QuitJob quitJob);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 离职申请
     * @param params
     * @return
     */
    Result quitJobApply(JSONObject params);
}