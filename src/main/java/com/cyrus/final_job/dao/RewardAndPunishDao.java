package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.RewardAndPunish;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工账套表(RewardAndPunish)表数据库访问层
 *
 * @author cyrus
 * @since 2020-03-09 19:41:41
 */
public interface RewardAndPunishDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RewardAndPunish queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset   查询起始位置
     * @param pageSize 查询条数
     * @return 对象列表
     */
    List<RewardAndPunish> queryAllByLimit(@Param("offset") int offset, @Param("pageSize") int pageSize);

//    List<RewardAndPunish> queryAllByLimitCondition(RewardAndPunishCondition condition);
//
//    Long queryAllByLimitConditionAccount(RewardAndPunishCondition condition);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param rewardAndPunish 实例对象
     * @return 对象列表
     */
    List<RewardAndPunish> queryAll(RewardAndPunish rewardAndPunish);

    /**
     * 新增数据
     *
     * @param rewardAndPunish 实例对象
     * @return 影响行数
     */
    int insert(RewardAndPunish rewardAndPunish);

    /**
     * 修改数据
     *
     * @param rewardAndPunish 实例对象
     * @return 影响行数
     */
    int update(RewardAndPunish rewardAndPunish);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<RewardAndPunish> queryByUserIdAndCreateTime(@Param("userId") Integer userId,
                                                     @Param("startTime") LocalDate startTime,
                                                     @Param("endTime") LocalDate endTime);
}