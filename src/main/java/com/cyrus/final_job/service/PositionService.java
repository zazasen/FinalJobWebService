package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.Position;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;

import java.util.List;

/**
 * 职位表(Position)表服务接口
 *
 * @author makejava
 * @since 2020-02-19 21:03:21
 */
public interface PositionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Position queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Position> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param position 实例对象
     * @return 实例对象
     */
    Position insert(Position position);

    /**
     * 修改数据
     *
     * @param position 实例对象
     * @return 实例对象
     */
    Position update(Position position);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 获取职位等级
     *
     * @return res
     */
    Result getLevel();

    /**
     * 查询职位
     *
     * @param params params
     * @return res
     */
    ResultPage getPosition(JSONObject params);

    /**
     * 添加职位
     *
     * @param params params
     * @return res
     */
    Result addPosition(JSONObject params);

    /**
     * 更新职位
     *
     * @param params params
     * @return res
     */
    Result updatePosition(JSONObject params);

    /**
     * 删除职位
     *
     * @param params params
     * @return res
     */
    Result delPosition(JSONObject params);

    /**
     * 批量删除职位
     *
     * @param params params
     * @return res
     */
    Result delPositionsByIds(JSONObject params);
}