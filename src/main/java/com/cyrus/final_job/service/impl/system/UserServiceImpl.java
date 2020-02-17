package com.cyrus.final_job.service.impl.system;

import com.cyrus.final_job.dao.system.RoleDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.dao.system.UserRoleDao;
import com.cyrus.final_job.entity.system.Role;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.entity.system.UserRole;
import com.cyrus.final_job.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author cyrus
 * @since 2020-02-16 14:53:32
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        this.userDao.update(user);
        return this.queryById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userDao.deleteById(id) > 0;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 根据用户id 从 user_role 表中查找出所有的 role id;
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        List<UserRole> userRoles = userRoleDao.queryAll(userRole);
        List<Integer> roleIds = userRoles.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        // 获取该用户下的所有角色信息
        List<Role> roles = roleDao.getRolesByIds(roleIds);
        user.setRoles(roles);
        return user;
    }
}