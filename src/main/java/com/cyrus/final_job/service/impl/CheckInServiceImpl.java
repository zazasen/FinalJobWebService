package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.ApprovalRecordDao;
import com.cyrus.final_job.dao.CheckInDao;
import com.cyrus.final_job.dao.DepartmentDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.ApprovalRecord;
import com.cyrus.final_job.entity.CheckIn;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.condition.CheckInCondition;
import com.cyrus.final_job.entity.condition.RemedySignCondition;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.entity.vo.CheckInRecordVo;
import com.cyrus.final_job.entity.vo.CheckInStatisticsVo;
import com.cyrus.final_job.entity.vo.SignCalendarVo;
import com.cyrus.final_job.enums.*;
import com.cyrus.final_job.service.CheckInService;
import com.cyrus.final_job.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签到表(CheckIn)表服务实现类
 *
 * @author cyrus
 * @since 2020-02-25 13:06:41
 */
@Service("checkInService")
public class CheckInServiceImpl implements CheckInService {
    @Autowired
    private CheckInDao checkInDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private ApprovalRecordDao approvalRecordDao;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public CheckIn queryById(Integer id) {
        return this.checkInDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<CheckIn> queryAllByLimit(int offset, int limit) {
        return this.checkInDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param checkIn 实例对象
     * @return 实例对象
     */
    @Override
    public CheckIn insert(CheckIn checkIn) {
        this.checkInDao.insert(checkIn);
        return checkIn;
    }

    /**
     * 修改数据
     *
     * @param checkIn 实例对象
     * @return 实例对象
     */
    @Override
    public CheckIn update(CheckIn checkIn) {
        this.checkInDao.update(checkIn);
        return this.queryById(checkIn.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.checkInDao.deleteById(id) > 0;
    }

    @Override
    public Result signIn() {
        CheckIn checkIn = CheckIn.signIn();
        checkIn.setEnabled(true);
        checkInDao.insert(checkIn);
        return Results.createOk("签到成功");
    }

    @Override
    public Result signOut() {
        CheckIn checkIn = new CheckIn();
        checkIn.setUserId(UserUtils.getCurrentUserId());
        checkIn.setCreateTime(LocalDate.now().toString());
        CheckIn res = checkInDao.queryByIdAndCreateTime(checkIn);
        if (res == null) {
            res = CheckIn.signOut(res);
            res.setEnabled(true);
            checkInDao.insert(res);
            return Results.createOk("签退成功");
        }
        res = CheckIn.signOut(res);
        checkInDao.update(res);
        return Results.createOk("签退成功");
    }

    @Override
    public Result signType() {
        CheckIn checkIn = new CheckIn();
        checkIn.setUserId(UserUtils.getCurrentUserId());
        checkIn.setCreateTime(LocalDate.now().toString());
        CheckIn res = checkInDao.queryByIdAndCreateTime(checkIn);
        Map<String, Integer> map = new HashMap<>();
        // 打卡类型为下班打卡
        if (res != null) {
            map.put("type", 1);
            return Results.createOk(map);
        }
        if (DateUtils.getNowHour() >= 19) {
            map.put("type", 1);
        } else {
            // 打卡类型为上班打卡
            map.put("type", 0);
        }
        return Results.createOk(map);
    }

    @Override
    public Result calendarShow() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonth().getValue();
        int days = now.getDayOfMonth();
        String s;
        if (month < 10) {
            s = year + "-0" + month + "-";
        } else {
            s = year + "-" + month + "-";
        }
        List<SignCalendarVo> list = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            if (i < 10) {
                s = s + "0" + i;
            } else {
                s = s + i;
            }
            CheckIn checkIn = new CheckIn();
            checkIn.setUserId(UserUtils.getCurrentUserId());
            checkIn.setCreateTime(s);
            CheckIn check = checkInDao.queryByIdAndCreateTime(checkIn);
            if (check != null) {
                // 该天已签到
                SignCalendarVo vo = new SignCalendarVo();
                vo.setDate(s);
                vo.setStatus(SignTypeEnum.getEnumByCode(check.getSignType()).getDesc());
                list.add(vo);
            } else {
                LocalDate parse = LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                // 如果不是节假日
                if (!CommonUtils.FreeDayJudge(parse)) {
                    // 该天未签到
                    SignCalendarVo vo = new SignCalendarVo();
                    vo.setDate(s);
                    vo.setStatus("未签到");
                    list.add(vo);
                }
            }
            if (month < 10) {
                s = year + "-0" + month + "-";
            } else {
                s = year + "-" + month + "-";
            }
        }
        return Results.createOk(list);
    }


    @Override
    public ResultPage getAttendanceRecord(JSONObject params) {
        CheckInCondition condition = params.toJavaObject(CheckInCondition.class);
        condition.buildLimit();
        condition.buildTime();
        condition.setUserId(UserUtils.getCurrentUserId());

        List<CheckInRecordVo> vos = buildAttendanceRecord(condition);
        Long total = checkInDao.queryAllByConditionCount(condition);
        return Results.createOk(total, vos);
    }

    @Override
    public ResultPage getAllAttendanceRecord(JSONObject params) {
        CheckInCondition condition = params.toJavaObject(CheckInCondition.class);
        condition.buildLimit();
        condition.buildTime();
        List<CheckInRecordVo> vos = buildAttendanceRecord(condition);
        Long total = checkInDao.queryAllByConditionCount(condition);
        return Results.createOk(total, vos);
    }

    private List<CheckInRecordVo> buildAttendanceRecord(CheckInCondition condition) {
        List<CheckIn> list = checkInDao.queryAllByCondition(condition);
        List<CheckInRecordVo> vos = JSONArray.parseArray(JSONArray.toJSONString(list), CheckInRecordVo.class);
        for (CheckInRecordVo vo : vos) {
            if (vo.getStartType() != null) {
                vo.setStartTypeStr(SignInTypeEnum.getEnumByCode(vo.getStartType()).getDesc());
            }
            if (vo.getEndType() != null) {
                vo.setEndTypeStr(SignInTypeEnum.getEnumByCode(vo.getEndType()).getDesc());
            }
            if (vo.getSignType() != null) {
                vo.setSignTypeStr(SignTypeEnum.getEnumByCode(vo.getSignType()).getDesc());
            }
            User user = userDao.queryById(vo.getUserId());
            vo.setUsername(user.getRealName());
            if (vo.getWorkHours() != null) {
                vo.setWorkHoursStr(vo.getWorkHours() + "小时");
            }
        }
        return vos;
    }

    @Override
    public Result remedySign(JSONObject params) {
        RemedySignCondition condition = params.toJavaObject(RemedySignCondition.class);
        if (condition.getUserId() == null) return Results.error("userId 不能为空");
        if (condition.getApplyType() == null) return Results.error("补卡类型不能为空");
        ApplyTypeEnum applyTypeEnum = ApplyTypeEnum.getEnumByCode(condition.getApplyType());

        switch (applyTypeEnum) {
            // 签到补卡
            case START_SIGN_APPlY:
                return startApplySign(condition);
            case END_SIGN_APPLY:
                return endApplySign(condition);
            default:
                break;
        }

        return Results.createOk("申请失败");
    }

    /**
     * 补卡类型为签退补卡
     *
     * @param condition
     */
    private Result endApplySign(RemedySignCondition condition) {
        User user = userDao.queryById(condition.getUserId());
        ApprovalRecord approvalRecord = new ApprovalRecord();
        Integer managerId = departmentDao.queryById(user.getDepartmentId()).getUserId();
        approvalRecord.setProduceUserId(user.getId());
        approvalRecord.setApprovalType(ApprovalTypeEnum.END_REMEDY_SIGN.getCode());
        approvalRecord.setApprovalUserId(managerId);
        approvalRecord.setRecordStatus(RecordStatusEnum.READY_PASS.getCode());
        approvalRecord.setCreateTime(DateUtils.getNowTime());
        LocalDate createTime = condition.getEndTime().toLocalDateTime().toLocalDate();

        // 要被更新的记录
        CheckIn checkIn = checkInDao.queryByCreateTime(createTime.toString(), user.getId());
        checkIn.setId(null);
        checkIn.setEndTime(condition.getEndTime());
        checkIn.setEndType(SignInTypeEnum.REMEDY_SIGN.getCode());
        checkIn.setEnabled(EnableBooleanEnum.DISABLE.getCode());
        if (checkIn.getStartTime() != null) {
            checkIn.setSignType(SignTypeEnum.FULL.getCode());
            double workHours = DateUtils.getGapTime(checkIn.getStartTime().toLocalDateTime(), condition.getEndTime().toLocalDateTime());
            checkIn.setWorkHours(workHours);
        } else {
            checkIn.setSignType(SignTypeEnum.HALF.getCode());
            checkIn.setWorkHours(4.0);
        }
        checkInDao.insert(checkIn);
        approvalRecord.setApprovalId(checkIn.getId());
        approvalRecordDao.insert(approvalRecord);
        return Results.createOk("已发起申请");
    }

    /**
     * 补卡类型为签到补卡
     *
     * @param condition
     */
    private Result startApplySign(RemedySignCondition condition) {

        User user = userDao.queryById(condition.getUserId());
        ApprovalRecord approvalRecord = new ApprovalRecord();
        Integer managerId = departmentDao.queryById(user.getDepartmentId()).getUserId();
        approvalRecord.setProduceUserId(user.getId());
        approvalRecord.setApprovalType(ApprovalTypeEnum.START_REMEDY_SIGN.getCode());
        approvalRecord.setApprovalUserId(managerId);
        approvalRecord.setRecordStatus(RecordStatusEnum.READY_PASS.getCode());
        approvalRecord.setCreateTime(DateUtils.getNowTime());
        LocalDate createTime = condition.getStartTime().toLocalDateTime().toLocalDate();

        // 要被更新的记录
        CheckIn checkIn = checkInDao.queryByCreateTime(createTime.toString(), user.getId());
        checkIn.setId(null);
        checkIn.setStartTime(condition.getStartTime());
        checkIn.setStartType(SignInTypeEnum.REMEDY_SIGN.getCode());
        checkIn.setEnabled(EnableBooleanEnum.DISABLE.getCode());
        if (checkIn.getEndTime() != null) {
            checkIn.setSignType(SignTypeEnum.FULL.getCode());
            double workHours = DateUtils.getGapTime(condition.getStartTime().toLocalDateTime(), checkIn.getEndTime().toLocalDateTime());
            checkIn.setWorkHours(workHours);
        } else {
            checkIn.setSignType(SignTypeEnum.HALF.getCode());
            checkIn.setWorkHours(4.0);
        }
        checkInDao.insert(checkIn);
        approvalRecord.setApprovalId(checkIn.getId());
        approvalRecordDao.insert(approvalRecord);
        return Results.createOk("已发起申请");
    }

    @Override
    public Result getShouldBeWorkDays() {
        int month = LocalDate.now().getMonth().getValue();
        String key = RedisKeys.shouldBeWorkDaysKey(month);
        String days = redisUtil.get(key);
        if (days == null) {
            days = CommonUtils.shouldBeWorkDays().toString();
            redisUtil.set(key, days);
        }
        List<CheckInStatisticsVo> vos = new ArrayList<>();

        CheckInCondition condition = new CheckInCondition();
        condition.setUserId(UserUtils.getCurrentUserId());
        condition.setBeginTime(DateUtils.getCurrentMonthFirstDay().toString());
        condition.setTailTime(LocalDate.now().toString());
        List<CheckIn> list = checkInDao.queryAllByConditionNoPage(condition);

        int workDays = 0;
        int leaveDays = 0;
        for (CheckIn check : list) {
            if (SignTypeEnum.HALF.getCode().equals(check.getSignType()) || SignTypeEnum.FULL.getCode().equals(check.getSignType())) {
                workDays++;
            }
            if (SignTypeEnum.FREE.getCode().equals(check.getSignType())) {
                leaveDays++;
            }
        }
        // 已打卡天数
        CheckInStatisticsVo vo = new CheckInStatisticsVo();
        vo.setName("已打卡");
        vo.setValue(workDays);
        vos.add(vo);

        //请假天数
        CheckInStatisticsVo vo1 = new CheckInStatisticsVo();
        vo1.setName("请假");
        vo1.setValue(leaveDays);
        vos.add(vo1);

        //未打卡天数
        CheckInStatisticsVo vo2 = new CheckInStatisticsVo();
        vo2.setName("未打卡");
        vo2.setValue(Integer.valueOf(days) - workDays);
        vos.add(vo2);
        return Results.createOk(vos);
    }
}