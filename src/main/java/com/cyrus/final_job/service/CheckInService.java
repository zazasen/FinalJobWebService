package com.cyrus.final_job.service;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.CheckIn;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;

import java.util.List;

/**
 * 签到表(CheckIn)表服务接口
 *
 * @author cyrus
 * @since 2020-02-25 13:06:41
 */
public interface CheckInService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CheckIn queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<CheckIn> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param checkIn 实例对象
     * @return 实例对象
     */
    CheckIn insert(CheckIn checkIn);

    /**
     * 修改数据
     *
     * @param checkIn 实例对象
     * @return 实例对象
     */
    CheckIn update(CheckIn checkIn);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 签到
     *
     * @return
     */
    Result signIn();


    /**
     * 签出
     *
     * @return
     */
    Result signOut();

    /**
     * 当前的签到类型
     *
     * @return
     */
    Result signType();

    Result calendarShow();

    /**
     * 获取考个人勤记录
     *
     * @param params
     * @return
     */
    ResultPage getAttendanceRecord(JSONObject params);

    /**
     * 获取所有员工考勤记录
     *
     * @param params
     * @return
     */
    ResultPage getAllAttendanceRecord(JSONObject params);

    /**
     * 补签
     *
     * @param params
     * @return
     */
    Result remedySign(JSONObject params);

    /**
     * 获取当月应该工作的天数
     *
     * @return
     */
    Result getShouldBeWorkDays();

    /**
     * 获取异常考勤
     *
     * @return
     */
    Result getExceptionCheckIn();

    /**
     * 手动补卡
     *
     * @param params
     * @return
     */
    Result updateCheckIn(JSONObject params);
}