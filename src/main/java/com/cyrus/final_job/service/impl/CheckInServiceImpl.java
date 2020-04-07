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
import com.cyrus.final_job.entity.condition.CheckInStatisticsCondition;
import com.cyrus.final_job.entity.condition.RemedySignCondition;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.entity.vo.CheckInRecordVo;
import com.cyrus.final_job.entity.vo.CheckInStatisticsDateVo;
import com.cyrus.final_job.entity.vo.CheckInStatisticsVo;
import com.cyrus.final_job.entity.vo.SignCalendarVo;
import com.cyrus.final_job.enums.*;
import com.cyrus.final_job.service.CheckInService;
import com.cyrus.final_job.utils.CommonUtils;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        // 每月要出勤的天数
        Integer days = CommonUtils.shouldBeWorkDays();

        List<CheckInStatisticsVo> vos = new ArrayList<>();
        CheckInCondition condition = new CheckInCondition();
        condition.setUserId(UserUtils.getCurrentUserId());
        condition.setBeginTime(DateUtils.getCurrentMonthFirstDay().toString());
        condition.setTailTime(LocalDate.now().toString());
        List<CheckIn> list = checkInDao.queryAllByConditionNoPage(condition);

        int workDays = 0;
        int leaveDays = 0;
        int weekendDays = 0;
        for (CheckIn check : list) {
            LocalDate parse = LocalDate.parse(check.getCreateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (CommonUtils.FreeDayJudge(parse)) {
                weekendDays++;
                continue;
            }
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
        vo2.setValue(days - workDays - leaveDays);
        vos.add(vo2);

        // 假期打卡
        CheckInStatisticsVo vo3 = new CheckInStatisticsVo();
        vo3.setName("休息日打卡");
        vo3.setValue(weekendDays);
        vos.add(vo3);
        return Results.createOk(vos);
    }

    @Override
    public Result getExceptionCheckIn() {
        Integer userId = UserUtils.getCurrentUserId();
        CheckInCondition condition = new CheckInCondition();
        condition.setUserId(userId);
        condition.setBeginTime(DateUtils.getCurrentMonthFirstDay().toString());
        condition.setTailTime(DateUtils.getCurrentMonthLasterDay().toString());
        List<CheckIn> checkIns = checkInDao.queryAllByConditionNoPage(condition);
        int later = 0;
        int early = 0;
        double workedTime = 0;
        for (CheckIn checkIn : checkIns) {
            // 如果当天上班打卡时间早于9点，算迟到
            if (checkIn.getStartTime() != null && checkIn.getStartTime().toLocalDateTime().isAfter(
                    LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(),
                            LocalDate.now().getDayOfMonth(), 9, 00))) {
                later++;
            }

            // 如果当天下班打卡时间早于下午6点算早退
            if (checkIn.getEndTime() != null && checkIn.getEndTime().toLocalDateTime().isBefore(
                    LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(),
                            LocalDate.now().getDayOfMonth(), 18, 00))) {
                early++;
            }
            if (checkIn.getWorkHours() != null) {
                workedTime = workedTime + checkIn.getWorkHours();
            }
        }
        List<CheckInStatisticsVo> vos = new ArrayList<>();
        CheckInStatisticsVo vo1 = new CheckInStatisticsVo();
        vo1.setName("早退");
        vo1.setValue(early);
        CheckInStatisticsVo vo2 = new CheckInStatisticsVo();
        vo2.setName("迟到");
        vo2.setValue(later);
        CheckInStatisticsVo vo3 = new CheckInStatisticsVo();
        vo3.setName("已工作工时");
        vo3.setValue(workedTime);
        vos.add(vo1);
        vos.add(vo2);
        vos.add(vo3);
        return Results.createOk(vos);
    }

    @Override
    public Result updateCheckIn(JSONObject params) {
        Integer id = params.getInteger("id");
        if (Objects.isNull(id)) {
            return Results.error("id 不能为空");
        }
        CheckIn checkIn = checkInDao.queryById(id);
        checkIn.setStartTime(DateUtils.getBeginWorkTime());
        checkIn.setStartType(SignInTypeEnum.NORMAL.getCode());
        checkIn.setEndTime(DateUtils.getEndWorkTime());
        checkIn.setEndType(SignInTypeEnum.NORMAL.getCode());
        checkIn.setSignType(SignTypeEnum.FULL.getCode());
        checkIn.setWorkHours(DateUtils.getGapTime(checkIn.getStartTime().toLocalDateTime(), checkIn.getEndTime().toLocalDateTime()));
        checkInDao.update(checkIn);
        return Results.createOk("人工补卡成功");
    }

    @Override
    public Result getStatisticsDate(JSONObject params) {
        CheckInStatisticsCondition condition = params.toJavaObject(CheckInStatisticsCondition.class);
        if (Objects.isNull(condition.getDate())) {
            // 如果没有选取时间，则显示最近一天工作日的考勤情况
            long workDay = new Date().getTime();
            while (true) {
                workDay = workDay - 3600 * 1000 * 24;
                Timestamp timestamp = new Timestamp(workDay);
                if (CommonUtils.FreeDayJudge(timestamp.toLocalDateTime().toLocalDate())) {
                    continue;
                } else {
                    condition.setDate(timestamp);
                    break;
                }
            }
        }
        if (condition.getDate().toLocalDateTime().toLocalDate().isAfter(LocalDate.now())) {
            return Results.error("请选择昨天及昨天啊以前的日期，今天及今天以后的统计还未出结果哦");
        }
        if (CommonUtils.FreeDayJudge(condition.getDate().toLocalDateTime().toLocalDate())) {
            return Results.error("请选择节假日以外的时间");
        }
        condition.setCreateTime(condition.getDate().toLocalDateTime().toLocalDate().toString());
        List<CheckIn> checkIns = checkInDao.queryStatisticsByCondition(condition);
        int half = 0;
        int one = 0;
        int free = 0;
        int exception = 0;
        for (CheckIn checkIn : checkIns) {
            if (Objects.equals(SignTypeEnum.HALF, SignTypeEnum.getEnumByCode(checkIn.getSignType()))) {
                half++;
            }
            if (Objects.equals(SignTypeEnum.FULL, SignTypeEnum.getEnumByCode(checkIn.getSignType()))) {
                one++;
            }
            if (Objects.equals(SignTypeEnum.FREE, SignTypeEnum.getEnumByCode(checkIn.getSignType()))) {
                free++;
            }
            if (Objects.equals(SignTypeEnum.NONE, SignTypeEnum.getEnumByCode(checkIn.getSignType()))) {
                exception++;
            }
        }
        CheckInStatisticsDateVo vo = new CheckInStatisticsDateVo();
        vo.setDate(condition.getDate());
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(half));
        list.add(String.valueOf(one));
        list.add(String.valueOf(free));
        list.add(String.valueOf(exception));
        vo.setData(list);
        return Results.createOk(vo);
    }
}