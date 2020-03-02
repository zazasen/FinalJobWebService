package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.HolidayDao;
import com.cyrus.final_job.entity.Holiday;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.HolidayService;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * 员工假期表(Holiday)表服务实现类
 *
 * @author cyrus
 * @since 2020-03-01 10:36:27
 */
@Service("holidayService")
public class HolidayServiceImpl implements HolidayService {
    @Resource
    private HolidayDao holidayDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Holiday queryById(Integer id) {
        return this.holidayDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Holiday> queryAllByLimit(int offset, int limit) {
        return this.holidayDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param holiday 实例对象
     * @return 实例对象
     */
    @Override
    public Holiday insert(Holiday holiday) {
        this.holidayDao.insert(holiday);
        return holiday;
    }

    /**
     * 修改数据
     *
     * @param holiday 实例对象
     * @return 实例对象
     */
    @Override
    public Holiday update(Holiday holiday) {
        this.holidayDao.update(holiday);
        return this.queryById(holiday.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.holidayDao.deleteById(id) > 0;
    }

    @Override
    public Result getMyHolidays(JSONObject params) {
        Integer holidayType = params.getInteger("holidayType");
        if (holidayType == null) return Results.error("holidayType 不能为空");
        int userId = UserUtils.getCurrentUserId();
        Holiday holiday = new Holiday();
        holiday.setUserId(userId);
        holiday.setHolidayType(holidayType);
        holiday.setCreateTime(String.valueOf(LocalDate.now().getYear()));
        Holiday res = holidayDao.queryByUserIdAndTypeInCurrentYear(holiday);

        return Results.createOk(res);
    }
}