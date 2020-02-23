package com.cyrus.final_job.dao.system;

import com.cyrus.final_job.entity.condition.UserAccountQueryCondition;
import com.cyrus.final_job.entity.condition.UserCondition;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.entity.vo.UserAccountVo;
import com.cyrus.final_job.entity.vo.UserDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表(User)表数据库访问层
 *
 * @author cyrus
 * @since 2020-02-16 14:53:32
 */
public interface UserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<UserDetailVo> queryStaffByCondition(UserCondition userCondition);

    List<UserDetailVo> export();

    Long queryStaffCountByCondition(UserCondition userCondition);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    List<User> queryAll(User user);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    User loadUserByUsername(String username);

    User queryByDepartmentId(Integer depId);

    Long getMaxWorkId();

    List<UserAccountVo> queryUserAccountByCondition(UserAccountQueryCondition condition);
    Long queryUserAccountCountByCondition(UserAccountQueryCondition condition);
}