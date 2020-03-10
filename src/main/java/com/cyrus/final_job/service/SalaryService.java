package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.Salary;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;

import java.util.List;

/**
 * 员工账套表(Salary)表服务接口
 *
 * @author cyrus
 * @since 2020-03-10 13:51:03
 */
public interface SalaryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Salary queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Salary> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param salary 实例对象
     * @return 实例对象
     */
    Salary insert(Salary salary);

    /**
     * 修改数据
     *
     * @param salary 实例对象
     * @return 实例对象
     */
    Salary update(Salary salary);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 月末处理
     *
     * @return
     */
    Result salaryHandle();

    /**
     * 获取薪资数据
     *
     * @param params
     * @return
     */
    ResultPage getAllSalary(JSONObject params);


    /**
     * 补差价
     * @param params
     * @return
     */
    Result editSalary(JSONObject params);
}