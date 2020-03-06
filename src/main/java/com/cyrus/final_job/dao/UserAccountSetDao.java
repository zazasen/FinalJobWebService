package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.UserAccountSet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工账套表(UserAccountSet)表数据库访问层
 *
 * @author cyrus
 * @since 2020-03-06 10:01:21
 */
public interface UserAccountSetDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserAccountSet queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserAccountSet> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param userAccountSet 实例对象
     * @return 对象列表
     */
    List<UserAccountSet> queryAll(UserAccountSet userAccountSet);

    /**
     * 新增数据
     *
     * @param userAccountSet 实例对象
     * @return 影响行数
     */
    int insert(UserAccountSet userAccountSet);

    /**
     * 修改数据
     *
     * @param userAccountSet 实例对象
     * @return 影响行数
     */
    int update(UserAccountSet userAccountSet);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    UserAccountSet queryByUserId(Integer userId);
}