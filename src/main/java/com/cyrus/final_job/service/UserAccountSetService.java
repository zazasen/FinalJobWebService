package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.UserAccountSet;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;

import java.util.List;

/**
 * 员工账套表(UserAccountSet)表服务接口
 *
 * @author cyrus
 * @since 2020-03-06 10:01:22
 */
public interface UserAccountSetService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserAccountSet queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<UserAccountSet> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param userAccountSet 实例对象
     * @return 实例对象
     */
    UserAccountSet insert(UserAccountSet userAccountSet);

    /**
     * 修改数据
     *
     * @param userAccountSet 实例对象
     * @return 实例对象
     */
    UserAccountSet update(UserAccountSet userAccountSet);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 查询员工薪资账套设置
     *
     * @param params
     * @return
     */
    ResultPage getUserAccountSet(JSONObject params);

    /**
     * 更新员工账套
     *
     * @param params
     * @return
     */
    Result updateUserAccountSet(JSONObject params);
}