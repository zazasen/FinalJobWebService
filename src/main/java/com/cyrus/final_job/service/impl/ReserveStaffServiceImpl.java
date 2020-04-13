package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.DepartmentDao;
import com.cyrus.final_job.dao.PositionDao;
import com.cyrus.final_job.dao.ReserveStaffDao;
import com.cyrus.final_job.entity.ReserveStaff;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.condition.ReserveStaffCondition;
import com.cyrus.final_job.entity.vo.ReserveStaffVo;
import com.cyrus.final_job.enums.ReserveStaffStatusEnum;
import com.cyrus.final_job.service.ReserveStaffService;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 预备员工表(ReserveStaff)表服务实现类
 *
 * @author cyrus
 * @since 2020-04-13 10:33:11
 */
@Service("reserveStaffService")
public class ReserveStaffServiceImpl implements ReserveStaffService {
    @Resource
    private ReserveStaffDao reserveStaffDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private PositionDao positionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ReserveStaff queryById(Integer id) {
        return this.reserveStaffDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ReserveStaff> queryAllByLimit(int offset, int limit) {
        return this.reserveStaffDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param reserveStaff 实例对象
     * @return 实例对象
     */
    @Override
    public ReserveStaff insert(ReserveStaff reserveStaff) {
        this.reserveStaffDao.insert(reserveStaff);
        return reserveStaff;
    }

    /**
     * 修改数据
     *
     * @param reserveStaff 实例对象
     * @return 实例对象
     */
    @Override
    public ReserveStaff update(ReserveStaff reserveStaff) {
        this.reserveStaffDao.update(reserveStaff);
        return this.queryById(reserveStaff.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.reserveStaffDao.deleteById(id) > 0;
    }

    @Override
    public ResultPage getAllReserveStaff(JSONObject params) {
        ReserveStaffCondition condition = params.toJavaObject(ReserveStaffCondition.class);
        condition.buildLimit();
        List<ReserveStaff> reserveStaffs = reserveStaffDao.getAllReserveStaff(condition);
        Long total = reserveStaffDao.getAllReserveStaffCount(condition);
        List<ReserveStaffVo> vos = JSONArray.parseArray(JSONArray.toJSONString(reserveStaffs), ReserveStaffVo.class);
        for (ReserveStaffVo vo : vos) {
            vo.setDepartmentName(departmentDao.queryById(vo.getDepartmentId()).getName());
            vo.setPositionName(positionDao.queryById(vo.getPositionId()).getPositionName());
            vo.setStatusStr(ReserveStaffStatusEnum.getEnumByCode(vo.getStatus()).getDesc());
            vo.setEntryTimeStr(vo.getEntryTime().toLocalDateTime().toLocalDate().toString());
        }
        return Results.createOk(total, vos);
    }

    @Override
    public Result addReserveStaff(JSONObject params) {
        ReserveStaff reserveStaff = params.toJavaObject(ReserveStaff.class);
        Result result = reserveStaff.checkParams();
        if (result != null) return result;
        reserveStaff.setCreateTime(DateUtils.getNowTime());
        reserveStaffDao.insert(reserveStaff);
        return Results.createOk("添加成功");
    }

    @Override
    public Result updateReserveStaff(JSONObject params) {
        ReserveStaff reserveStaff = params.toJavaObject(ReserveStaff.class);
        Result result = reserveStaff.checkParams();
        if (result != null) return result;
        reserveStaffDao.update(reserveStaff);
        return Results.createOk("更新成功");
    }

    @Override
    public Result delReserveStaff(JSONObject params) {
        Integer id = params.getInteger("id");
        if (Objects.isNull(id)) {
            return Results.error("id 不能为空");
        }
        reserveStaffDao.deleteById(id);
        return Results.createOk("删除成功");
    }

    @Override
    public Result delReserveStaffs(JSONObject params) {
        List<Integer> ids = JSONArray.parseArray(params.getJSONArray("ids").toJSONString(), Integer.class);
        if (CollectionUtils.isEmpty(ids)) {
            return Results.error("ids 不能为空");
        }
        for (Integer id : ids) {
            reserveStaffDao.deleteById(id);
        }
        return Results.createOk("删除成功");
    }
}