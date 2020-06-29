package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.Department;
import com.cyrus.final_job.entity.base.Result;

import java.util.List;

/**
 * 部门表(Department)表服务接口
 *
 * @author cyrus
 * @since 2020-02-19 13:34:25
 */
public interface DepartmentService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Department queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Department> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param department 实例对象
     * @return 实例对象
     */
    Department insert(Department department);

    Result addDep(JSONObject params);

    /**
     * 修改数据
     *
     * @param department 实例对象
     * @return 实例对象
     */
    Department update(Department department);

    Result updateDep(JSONObject params);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    Result delDep(JSONObject params);

    /**
     * 部门树
     *
     * @return res
     */
    Result getAllDepartment();

    /**
     * bp 获取部门
     *
     * @return
     */
    Result getDepByBp();

    /**
     * 获取指定部门底下的员工
     *
     * @param params
     * @return
     */
    Result getDepStaff(JSONObject params);

    /**
     * bp 自己兄弟部门以及父部门的所有员工
     *
     * @return
     */
    Result getBrotherAndFatherDepStaff(JSONObject params);
}