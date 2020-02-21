package com.cyrus.final_job.service;

import com.cyrus.final_job.entity.PoliticsStatus;
import com.cyrus.final_job.entity.base.Result;

import java.util.List;

/**
 * 政治面貌表(PoliticsStatus)表服务接口
 *
 * @author cyrus
 * @since 2020-02-20 16:00:32
 */
public interface PoliticsStatusService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PoliticsStatus queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<PoliticsStatus> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param politicsStatus 实例对象
     * @return 实例对象
     */
    PoliticsStatus insert(PoliticsStatus politicsStatus);

    /**
     * 修改数据
     *
     * @param politicsStatus 实例对象
     * @return 实例对象
     */
    PoliticsStatus update(PoliticsStatus politicsStatus);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 获取政治面貌下拉列表
     *
     * @return res
     */
    Result getPoliticsStatus();
}