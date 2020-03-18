package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.DepartmentDao;
import com.cyrus.final_job.dao.PositionDao;
import com.cyrus.final_job.dao.StaffNeedsDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.StaffNeeds;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.condition.StaffNeedsQueryCondition;
import com.cyrus.final_job.entity.vo.StaffNeedsQueryVo;
import com.cyrus.final_job.enums.*;
import com.cyrus.final_job.service.StaffNeedsService;
import com.cyrus.final_job.utils.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 招聘需求表(StaffNeeds)表服务实现类
 *
 * @author cyrus
 * @since 2020-03-17 17:07:52
 */
@Service("staffNeedsService")
public class StaffNeedsServiceImpl implements StaffNeedsService {
    @Resource
    private StaffNeedsDao staffNeedsDao;

    @Autowired
    private UserDao userDao;

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
    public StaffNeeds queryById(Integer id) {
        return this.staffNeedsDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<StaffNeeds> queryAllByLimit(int offset, int limit) {
        return this.staffNeedsDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param staffNeeds 实例对象
     * @return 实例对象
     */
    @Override
    public StaffNeeds insert(StaffNeeds staffNeeds) {
        this.staffNeedsDao.insert(staffNeeds);
        return staffNeeds;
    }

    /**
     * 修改数据
     *
     * @param staffNeeds 实例对象
     * @return 实例对象
     */
    @Override
    public StaffNeeds update(StaffNeeds staffNeeds) {
        this.staffNeedsDao.update(staffNeeds);
        return this.queryById(staffNeeds.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.staffNeedsDao.deleteById(id) > 0;
    }

    @Override
    public Result submitStaffNeed(JSONObject params) {
        StaffNeeds staffNeeds = params.toJavaObject(StaffNeeds.class);
        Result result = staffNeeds.checkParams();
        if (result != null) return result;
        staffNeedsDao.insert(staffNeeds);
        return Results.createOk("提交招聘需求成功");
    }

    @Override
    public ResultPage getStaffNeedsDate(JSONObject params) {
        StaffNeedsQueryCondition condition = params.toJavaObject(StaffNeedsQueryCondition.class);
        condition.buildLimit();
        List<StaffNeeds> list = staffNeedsDao.queryByQueryCondition(condition);
        List<StaffNeedsQueryVo> staffNeedsQueryVos = JSONArray.parseArray(JSONArray.toJSONString(list), StaffNeedsQueryVo.class);
        for (StaffNeedsQueryVo vo : staffNeedsQueryVos) {
            buildStaffNeedsQueryVo(vo);
        }
        Long total = staffNeedsDao.queryCountByQueryCondition(condition);
        return Results.createOk(total, staffNeedsQueryVos);
    }

    public void buildStaffNeedsQueryVo(StaffNeedsQueryVo vo) {
        vo.setRealName(userDao.queryById(vo.getUserId()).getRealName());
        vo.setDepartmentName(departmentDao.queryById(vo.getDepartmentId()).getName());
        vo.setPositionName(positionDao.queryById(vo.getPositionId()).getPositionName());
        vo.setReasonStr(RecruitReasonEnum.getEnumByCode(vo.getReason()).getDesc());
        if (vo.getGender() == null) {
            vo.setGenderStr("不限");
        } else {
            vo.setGenderStr(GenderEnum.getEnumByCode(vo.getGender()).getDesc());
        }
        vo.setDegreeStr(DegreeEnum.getEnumByCode(vo.getDegree()).getDesc() + "以上");
        if (vo.getWedlock() == null) {
            vo.setWedlockStr("不限");
        } else {
            vo.setWedlockStr(WedlockEnum.getEnumByCode(vo.getWedlock()).getDesc());
        }
        if (StringUtils.isEmpty(vo.getSpeciality())) {
            vo.setSpeciality("不限");
        }
        if (StringUtils.isEmpty(vo.getForeignLanguages())) {
            vo.setForeignLanguages("不限");
        }
        if (StringUtils.isEmpty(vo.getSkill())) {
            vo.setSkill("不限");
        }
        if (StringUtils.isEmpty(vo.getExperience())) {
            vo.setExperience("不限");
        }
        vo.setPublishStr(StaffNeedsPublishEnum.getEnumByCode(vo.getPublish()).getDesc());
    }

    @Override
    public Result getStaffNeedsDetail(JSONObject params) {
        Integer id = params.getInteger("id");
        if (id == null) return Results.error("id 不能为空");
        StaffNeeds staffNeeds = staffNeedsDao.queryById(id);
        StaffNeedsQueryVo vo = JSONObject.parseObject(JSONObject.toJSONString(staffNeeds), StaffNeedsQueryVo.class);
        buildStaffNeedsQueryVo(vo);
        return Results.createOk(vo);
    }

    @Override
    public Result editStaffNeeds(JSONObject params) {
        StaffNeeds condition = params.toJavaObject(StaffNeeds.class);
        if (condition.getId() == null) return Results.error("参数出错");
        staffNeedsDao.update(condition);
        return Results.createOk("操作成功");
    }

    @Override
    public ResultPage getPublishedStaffNeeds(JSONObject params) {
        StaffNeedsQueryCondition condition = params.toJavaObject(StaffNeedsQueryCondition.class);
        if (null == condition.getPageIndex() || condition.getPageIndex() <= 0) {
            condition.setPageIndex(1);
        }
        if (condition.getPageSize() == null || condition.getPageSize() <= 0) {
            condition.setPageSize(3);
        }
        condition.setOffset((condition.getPageIndex() - 1) * condition.getPageSize());
        condition.setPublish(StaffNeedsPublishEnum.PUBLISHED.getCode());
        List<StaffNeeds> list = staffNeedsDao.queryByQueryCondition(condition);
        Long total = staffNeedsDao.queryCountByQueryCondition(condition);
        List<StaffNeedsQueryVo> staffNeedsQueryVos = JSONArray.parseArray(JSONArray.toJSONString(list), StaffNeedsQueryVo.class);
        for (StaffNeedsQueryVo vo : staffNeedsQueryVos) {
            buildStaffNeedsQueryVo(vo);
        }
        return Results.createOk(total, staffNeedsQueryVos);
    }
}