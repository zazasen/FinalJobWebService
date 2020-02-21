package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.PositionDao;
import com.cyrus.final_job.entity.Position;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.vo.PositionLevelVo;
import com.cyrus.final_job.entity.vo.PositionVo;
import com.cyrus.final_job.enums.EnabledEnum;
import com.cyrus.final_job.enums.PositionLevelEnum;
import com.cyrus.final_job.service.PositionService;
import com.cyrus.final_job.utils.Results;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 职位表(Position)表服务实现类
 *
 * @author makejava
 * @since 2020-02-19 21:03:21
 */
@Service("positionService")
public class PositionServiceImpl implements PositionService {
    @Resource
    private PositionDao positionDao;



    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Position queryById(Integer id) {
        return this.positionDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Position> queryAllByLimit(int offset, int limit) {
        return this.positionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param position 实例对象
     * @return 实例对象
     */
    @Override
    public Position insert(Position position) {
        this.positionDao.insert(position);
        return position;
    }

    @Override
    public Result addPosition(JSONObject params) {
        Position position = params.toJavaObject(Position.class);
        Result result = position.checkParams();
        if (result != null) return result;
        positionDao.insert(position);
        return Results.createOk("添加职位成功");
    }

    /**
     * 修改数据
     *
     * @param position 实例对象
     * @return 实例对象
     */
    @Override
    public Position update(Position position) {
        this.positionDao.update(position);
        return this.queryById(position.getId());
    }

    @Override
    public Result updatePosition(JSONObject params) {
        Position position = params.toJavaObject(Position.class);
        if (Objects.isNull(position.getId())) {
            return Results.error("id 不能为空");
        }
        positionDao.update(position);
        return Results.createOk("更新成功");
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.positionDao.deleteById(id) > 0;
    }

    @Override
    public Result getLevel() {
        List<PositionLevelVo> positionLevelList = PositionLevelEnum.getPositionLevelList();
        return Results.createOk(positionLevelList);
    }

    @Override
    public ResultPage getPosition(JSONObject params) {
        Position position = params.toJavaObject(Position.class);
        position.buildLimit();
        List<Position> positions = positionDao.queryByLimit(position);

        List<PositionVo> positionVos = JSONArray.parseArray(JSONObject.toJSONString(positions), PositionVo.class);

        for (PositionVo vo : positionVos) {
            vo.setPositionLevelStr(PositionLevelEnum.getEnumByCode(vo.getPositionLevel()).getDesc());
            vo.setEnabledStr(EnabledEnum.getEnumByCode(vo.getEnabled()).getDesc());
        }

        Long total = positionDao.queryCount(position);
        return Results.createOk(total, positionVos);
    }

    @Override
    public Result delPosition(JSONObject params) {
        Integer id = params.getInteger("id");
        if (Objects.isNull(id)) return Results.error("id 不能为空");
        positionDao.deleteById(id);
        return Results.createOk("删除成功");
    }

    @Override
    public Result delPositionsByIds(JSONObject params) {
        JSONArray array = params.getJSONArray("ids");
        if (array == null || array.isEmpty()) return Results.error("ids 参数不能为空");
        List<Integer> ids = JSONArray.parseArray(JSONObject.toJSONString(array), Integer.class);
        positionDao.delByIds(ids);
        return Results.createOk("删除成功");
    }

    @Override
    public Result getPositions() {
        List<Position> positions = positionDao.queryAll(new Position());
        for (Position position : positions) {
            // 初级 资深java工程师样式
            position.setPositionName(PositionLevelEnum.getEnumByCode(position.getPositionLevel()).getDesc()
                    + "-" + position.getPositionName());
        }
        return Results.createOk(positions);
    }

}