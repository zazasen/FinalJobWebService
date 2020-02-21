package com.cyrus.final_job.dao;

import com.cyrus.final_job.entity.PoliticsStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 政治面貌表(PoliticsStatus)表数据库访问层
 *
 * @author cyrus
 * @since 2020-02-20 16:00:32
 */
public interface PoliticsStatusDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PoliticsStatus queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<PoliticsStatus> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param politicsStatus 实例对象
     * @return 对象列表
     */
    List<PoliticsStatus> queryAll(PoliticsStatus politicsStatus);

    /**
     * 新增数据
     *
     * @param politicsStatus 实例对象
     * @return 影响行数
     */
    int insert(PoliticsStatus politicsStatus);

    /**
     * 修改数据
     *
     * @param politicsStatus 实例对象
     * @return 影响行数
     */
    int update(PoliticsStatus politicsStatus);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}