package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.Contract;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;

import java.util.List;

/**
 * 员工合同表(Contract)表服务接口
 *
 * @author cyrus
 * @since 2020-03-12 15:22:35
 */
public interface ContractService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Contract queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Contract> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param contract 实例对象
     * @return 实例对象
     */
    Contract insert(Contract contract);

    /**
     * 修改数据
     *
     * @param contract 实例对象
     * @return 实例对象
     */
    Contract update(Contract contract);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 获取合同页面
     *
     * @param userId
     * @return
     */
    String getContract(Integer userId);

    /**
     * 获取合同列表
     *
     * @return
     */
    ResultPage getAllContract(JSONObject params);

    /**
     * 合同发起
     *
     * @return
     */
    Result addContract(JSONObject params);

    /**
     * 获取我的合同
     *
     * @return
     */
    Result getMyContract();

    /**
     * 确定合同
     *
     * @return
     */
    Result confirmAdd();

}