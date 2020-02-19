package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.DepartmentDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.Department;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.enums.IsParentEnum;
import com.cyrus.final_job.service.DepartmentService;
import com.cyrus.final_job.utils.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 部门表(Department)表服务实现类
 *
 * @author cyrus
 * @since 2020-02-19 13:34:26
 */
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Department queryById(Integer id) {
        return this.departmentDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Department> queryAllByLimit(int offset, int limit) {
        return this.departmentDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param department 实例对象
     * @return 实例对象
     */
    @Override
    public Department insert(Department department) {
        this.departmentDao.insert(department);
        return department;
    }

    @Override
    public Result addDep(JSONObject params) {
        Department department = params.toJavaObject(Department.class);
        Result result = department.checkParams();
        if (result != null) return result;
        Department parent = departmentDao.queryById(department.getParentId());
        parent.setIsParent(1);
        parent.setSonCount(parent.getSonCount() + 1);
        parent.setId(department.getParentId());
        departmentDao.update(parent);
        departmentDao.insert(department);
        return Results.createOk("增加成功");
    }

    /**
     * 修改数据
     *
     * @param department 实例对象
     * @return 实例对象
     */
    @Override
    public Department update(Department department) {
        this.departmentDao.update(department);
        return this.queryById(department.getId());
    }

    @Override
    public Result updateDep(JSONObject params) {
        Department department = params.toJavaObject(Department.class);
        if(department.getId() == null){
            return Results.error("入参id不能为空");
        }
        this.departmentDao.update(department);
        return Results.createOk("更新成功");
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.departmentDao.deleteById(id) > 0;
    }

    @Override
    public Result delDep(JSONObject params) {
        Department department = params.toJavaObject(Department.class);
        if (department.getId() == null) {
            return Results.error("部门id参数不能为空");
        }
        if (department.getParentId() == null) {
            return Results.error("父部门id参数不能为空");
        }
        User user = userDao.queryByDepartmentId(department.getId());
        if (user != null) {
            return Results.error("删除失败，该部门旗下还有员工！");
        }
        Department self = departmentDao.queryById(department.getId());
        if (Objects.equals(IsParentEnum.getEnumByCode(self.getIsParent()), IsParentEnum.ISPARENT)) {
            return Results.error("删除失败，该部门旗下还有子部门！");
        }
        Department parent = departmentDao.queryById(department.getParentId());
        if (parent.getSonCount() > 0) {
            parent.setSonCount(parent.getSonCount() - 1);
        }
        if (parent.getSonCount() == 0) {
            parent.setIsParent(IsParentEnum.NOTPARENT.getCode());
        }
        departmentDao.update(parent);
        departmentDao.deleteById(department.getId());
        return Results.createOk("删除成功");
    }

    @Override
    public Result getAllDepartment() {
        List<Department> departmentTree = departmentDao.getDepartmentByParentId(-1);

        return Results.createOk(departmentTree);
    }
}