package com.cyrus.final_job.service.impl.system;

import com.alibaba.fastjson.JSONArray;
import com.cyrus.final_job.dao.system.MenuDao;
import com.cyrus.final_job.dao.system.MenuRoleDao;
import com.cyrus.final_job.dao.system.RoleDao;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.system.Menu;
import com.cyrus.final_job.entity.system.Role;
import com.cyrus.final_job.service.system.MenuService;
import com.cyrus.final_job.utils.RedisKeys;
import com.cyrus.final_job.utils.RedisUtils;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单管理表(Menu)表服务实现类
 *
 * @author cyrus
 * @since 2020-02-16 14:53:30
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Autowired
    private MenuRoleDao menuRoleDao;


    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Menu queryById(Integer id) {
        return this.menuDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Menu> queryAllByLimit(int offset, int limit) {
        return this.menuDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    @Override
    public Menu insert(Menu menu) {
        this.menuDao.insert(menu);
        return menu;
    }

    /**
     * 修改数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    @Override
    public Menu update(Menu menu) {
        this.menuDao.update(menu);
        return this.queryById(menu.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.menuDao.deleteById(id) > 0;
    }

    @Override
    public Result getMenusByUserId() {
        int userId = UserUtils.getCurrentUserId();
        String key = RedisKeys.menusKey(userId);
        // 先去 redis 里获取菜单列表
        String menusStr = redisUtils.get(key);
        List<Menu> res = null;
        // 如果 redis 里没有获取到，那么查数据库，并将数据塞到 redis 里
        if (StringUtils.isEmpty(menusStr)) {
            List<Menu> menus = menuDao.getMenusByUserId(userId);
            res = menus.stream().sorted(Comparator.comparing(Menu::getId)).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(res)){
                redisUtils.set(key, res);
            }
        } else {
            res = JSONArray.parseArray(menusStr, Menu.class);
        }
        return Results.createOk(res);
    }

    @Override
    public List<Menu> getAllMenuWithRole() {
        List<Menu> menus = menuDao.queryAll(new Menu());
        for (Menu menu : menus) {
            List<Role> roles = new ArrayList<>();
            List<Integer> roleIds = menuRoleDao.getRoleIdsByMenuId(menu.getId());
            for (Integer roleId : roleIds) {
                Role role = roleDao.queryById(roleId);
                roles.add(role);
            }
            menu.setRoles(roles);
        }
        return menus;
    }

    public Result getAllMenuWithChildren() {
        List<Menu> menus = menuDao.getAllMenuWithChildren();
        return Results.createOk(menus);
    }
}