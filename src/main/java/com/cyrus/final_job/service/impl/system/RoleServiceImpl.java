package com.cyrus.final_job.service.impl.system;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.system.MenuRoleDao;
import com.cyrus.final_job.dao.system.RoleDao;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.system.MenuRole;
import com.cyrus.final_job.entity.system.Role;
import com.cyrus.final_job.entity.system.condition.RoleCondition;
import com.cyrus.final_job.service.system.RoleService;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色表(Role)表服务实现类
 *
 * @author cyrus
 * @since 2020-02-16 14:53:32
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private MenuRoleDao menuRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Role queryById(Integer id) {
        return this.roleDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Role> queryAllByLimit(int offset, int limit) {
        return this.roleDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    @Override
    public Role insert(Role role) {
        this.roleDao.insert(role);
        return role;
    }

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    @Override
    public Role update(Role role) {
        this.roleDao.update(role);
        return this.queryById(role.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param params
     * @return res
     */
    @Override
    public Result deleteById(JSONObject params) {
        Integer id = params.getInteger("id");
        if (id == null) return Results.error("参数不能为空");
        menuRoleDao.delByRoleId(id);
        roleDao.deleteById(id);
        return Results.createOk("删除成功");
    }

    @Override
    public ResultPage getAllRoles(JSONObject params) {
        Integer pageSize = params.getInteger("pageSize");
        Integer pageIndex = params.getInteger("pageIndex");
        if (pageSize == null || pageSize <= 0) pageSize = 8;
        if (pageIndex == null || pageIndex <= 0) pageIndex = 1;
        List<Role> roles = roleDao.queryAllByLimit((pageIndex - 1) * pageSize, pageSize);
        Long total = roleDao.queryAllCount();
        return Results.createOk(total, roles);
    }

    @Override
    public ResultPage query(JSONObject params) {
        RoleCondition roleCondition = params.toJavaObject(RoleCondition.class);
        roleCondition.buildLimit();
        List<Role> roles = roleDao.query(roleCondition);
        Long total = roleDao.queryCount(roleCondition);
        return Results.createOk(total, roles);
    }

    @Override
    public Result addRoleWithMenu(JSONObject params) {
        Role role = params.toJavaObject(Role.class);
        Result result = role.checkParams();
        if (result != null) return result;
        role.setCreateTime(DateUtils.getNowTime());
        role.setUpdateTime(DateUtils.getNowTime());
        roleDao.insert(role);
        String selectKeys = JSONObject.toJSONString(params.getJSONArray("selectKeys"));
        List<Integer> menuIds = JSONObject.parseArray(selectKeys, Integer.class);
        addMenuRole(menuIds, role);
        return Results.createOk("添加成功");
    }

    @Override
    public Result updateRole(JSONObject params) {
        Role role = params.toJavaObject(Role.class);
        Result result = role.checkParams();
        if (result != null) return result;
        role.setUpdateTime(DateUtils.getNowTime());
        roleDao.update(role);
        menuRoleDao.delByRoleId(role.getId());
        String selectKeys = JSONObject.toJSONString(params.getJSONArray("selectKeys"));
        List<Integer> menuIds = JSONObject.parseArray(selectKeys, Integer.class);
        addMenuRole(menuIds, role);
        return Results.createOk("添加成功");
    }

    private void addMenuRole(List<Integer> menuIds, Role role) {
        for (Integer menuId : menuIds) {
            MenuRole menuRole = new MenuRole();
            menuRole.setMenuId(menuId);
            menuRole.setRoleId(role.getId());
            menuRoleDao.insert(menuRole);
        }
    }

    @Override
    public Result delMulByIds(JSONObject params) {
        String s = JSONObject.toJSONString(params.getJSONArray("ids"));
        List<Integer> ids = JSONObject.parseArray(s, Integer.class);
        roleDao.delMulByIds(ids);
        menuRoleDao.delMulByRoleIds(ids);
        return Results.createOk("删除成功");
    }
}