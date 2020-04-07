package com.cyrus.final_job.dao.system;

import com.cyrus.final_job.entity.condition.*;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.entity.vo.UserAccountVo;
import com.cyrus.final_job.entity.vo.UserDetailVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
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
     * 无条件查找所有用户
     *
     * @return
     */
    List<User> selectAll();

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

    List<User> queryByDepartmentId(Integer depId);

    Long getMaxWorkId();

    /**
     * 员工账号
     *
     * @param condition
     * @return
     */
    List<UserAccountVo> queryUserAccountByCondition(UserAccountQueryCondition condition);

    Long queryUserAccountCountByCondition(UserAccountQueryCondition condition);

    /**
     * 员工薪资账套列表
     *
     * @param condition
     * @return
     */
    List<User> getUserByUserAccountSetCondition(UserAccountSetQueryCondition condition);

    Long getUserByUserAccountSetConditionCount(UserAccountSetQueryCondition condition);

    List<User> queryLeave(@Param("startTime") LocalDate startTime, @Param("endTime") LocalDate endTime);

    List<User> queryAllByContractCondition(ContractCondition condition);

    Long queryAllByContractConditionCount(ContractCondition condition);

    List<User> getAllUsersExceptOne(@Param("id") int id);

    /**
     * 根据月份统计入职人数
     *
     * @param condition
     * @return
     */
    long getUsersByCreateTime(HrStatisticsCondition condition);

    /**
     * 根据月份统计转正人数
     *
     * @param condition
     * @return
     */
    long getUsersByConversionTime(HrStatisticsCondition condition);

    /**
     * 根据月份统计离职人数
     *
     * @param condition
     * @return
     */
    long getUsersByDepartureTime(HrStatisticsCondition condition);

}