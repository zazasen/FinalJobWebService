package com.cyrus.final_job.service.impl.system;

import com.cyrus.final_job.dao.system.UserRoleDao;
import com.cyrus.final_job.entity.system.UserRole;
import com.cyrus.final_job.service.system.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色关联表(UserRole)表服务实现类
 *
 * @author cyrus
 * @since 2020-02-16 14:53:33
 */
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserRole queryById(Integer id) {
        return this.userRoleDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<UserRole> queryAllByLimit(int offset, int limit) {
        return this.userRoleDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param userRole 实例对象
     * @return 实例对象
     */
    @Override
    public UserRole insert(UserRole userRole) {
        this.userRoleDao.insert(userRole);
        return userRole;
    }

    /**
     * 修改数据
     *
     * @param userRole 实例对象
     * @return 实例对象
     */
    @Override
    public UserRole update(UserRole userRole) {
        this.userRoleDao.update(userRole);
        return this.queryById(userRole.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userRoleDao.deleteById(id) > 0;
    }
}