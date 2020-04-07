package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.AccountSet;
import com.cyrus.final_job.entity.condition.AccountSetCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 薪资账套表(AccountSet)表数据库访问层
 *
 * @author cyrus
 * @since 2020-03-05 14:10:16
 */
public interface AccountSetDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AccountSet queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset   查询起始位置
     * @param pageSize 查询条数
     * @return 对象列表
     */
    List<AccountSet> queryAllByLimit(@Param("offset") int offset, @Param("pageSize") int pageSize);

    List<AccountSet> queryByCondition(AccountSetCondition condition);


    Long queryByConditionCount();

    /**
     * 通过实体作为筛选条件查询
     *
     * @param accountSet 实例对象
     * @return 对象列表
     */
    List<AccountSet> queryAll(AccountSet accountSet);

    /**
     * 新增数据
     *
     * @param accountSet 实例对象
     * @return 影响行数
     */
    int insert(AccountSet accountSet);

    /**
     * 修改数据
     *
     * @param accountSet 实例对象
     * @return 影响行数
     */
    int update(AccountSet accountSet);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);
}