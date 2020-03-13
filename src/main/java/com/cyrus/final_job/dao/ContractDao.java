package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.Contract;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工合同表(Contract)表数据库访问层
 *
 * @author cyrus
 * @since 2020-03-12 15:22:34
 */
public interface ContractDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Contract queryById(Integer id);

    Contract queryByUserId(Integer id);


    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Contract> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param contract 实例对象
     * @return 对象列表
     */
    List<Contract> queryAll(Contract contract);

    /**
     * 新增数据
     *
     * @param contract 实例对象
     * @return 影响行数
     */
    int insert(Contract contract);

    /**
     * 修改数据
     *
     * @param contract 实例对象
     * @return 影响行数
     */
    int update(Contract contract);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}