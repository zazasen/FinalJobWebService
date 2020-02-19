package com.cyrus.final_job.dao.system;

import com.cyrus.final_job.entity.system.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单管理表(Menu)表数据库访问层
 *
 * @author cyrus
 * @since 2020-02-16 14:53:29
 */
public interface MenuDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Menu queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Menu> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param menu 实例对象
     * @return 对象列表
     */
    List<Menu> queryAll(Menu menu);

    /**
     * 新增数据
     *
     * @param menu 实例对象
     * @return 影响行数
     */
    int insert(Menu menu);

    /**
     * 修改数据
     *
     * @param menu 实例对象
     * @return 影响行数
     */
    int update(Menu menu);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 获取当前登录用户可以访问的菜单
     *
     * @param userId userid
     * @return 可以访问的菜单列表
     */
    List<Menu> getMenusByUserId(int userId);

    List<Menu> getAllMenuWithChildren();
}