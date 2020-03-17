package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.StaffNeedsDao;
import com.cyrus.final_job.entity.StaffNeeds;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.StaffNeedsService;
import com.cyrus.final_job.utils.Results;
import org.springframework.stereotype.Service;

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
}