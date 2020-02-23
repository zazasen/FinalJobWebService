package com.cyrus.final_job.service.system;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.system.Role;

import java.util.List;

/**
 * 角色表(Role)表服务接口
 *
 * @author cyrus
 * @since 2020-02-16 14:53:32
 */
public interface RoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Role queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Role> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    Role insert(Role role);

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    Role update(Role role);

    /**
     * 通过主键删除数据
     *
     * @param params
     * @return res
     */
    Result deleteById(JSONObject params);

    /**
     * 无条件查询所有角色
     *
     * @return res
     */
    ResultPage getAllRoles(JSONObject params);

    /**
     * 添加角色，添加角色的时候可以顺便把角色可以访问的资源也给加上
     *
     * @param params
     * @return
     */
    Result addRoleWithMenu(JSONObject params);

    /**
     * 批量删除角色
     *
     * @param params params
     * @return res
     */
    Result delMulByIds(JSONObject params);

    Result updateRole(JSONObject params);

    ResultPage query(JSONObject params);

    Result getAllRolesWithoutCondition();
}