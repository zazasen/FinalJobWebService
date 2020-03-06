package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.AccountSet;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;

import java.util.List;

/**
 * 薪资账套表(AccountSet)表服务接口
 *
 * @author cyrus
 * @since 2020-03-05 14:10:17
 */
public interface AccountSetService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AccountSet queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AccountSet> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param accountSet 实例对象
     * @return 实例对象
     */
    AccountSet insert(AccountSet accountSet);

    /**
     * 修改数据
     *
     * @param accountSet 实例对象
     * @return 实例对象
     */
    AccountSet update(AccountSet accountSet);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 添加薪资账套
     *
     * @param params
     * @return
     */
    Result addAccountSet(JSONObject params);

    /**
     * 查询薪资账套
     *
     * @param params
     * @return
     */
    ResultPage queryAccountSet(JSONObject params);

    /**
     * 更新账套
     *
     * @param params
     * @return
     */
    Result editAccountSet(JSONObject params);

    /**
     * 删除账套
     *
     * @param params
     * @return
     */
    Result delAccountSet(JSONObject params);

    /**
     * 获取薪资账套下拉列表
     *
     * @return
     */
    Result getAccountSet();
}