package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.*;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.*;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.entity.vo.ApprovalRecordVo;
import com.cyrus.final_job.enums.*;
import com.cyrus.final_job.service.ApprovalRecordService;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 审批人员表(ApprovalRecord)表服务实现类
 *
 * @author cyrus
 * @since 2020-02-27 11:36:00
 */
@Service("approvalRecordService")
public class ApprovalRecordServiceImpl implements ApprovalRecordService {
    @Resource
    private ApprovalRecordDao approvalRecordDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CheckInDao checkInDao;

    @Autowired
    private LeaveDao leaveDao;

    @Autowired
    private ApprovalFlowDao approvalFlowDao;

    @Autowired
    private HolidayDao holidayDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private OvertimeDao overtimeDao;

    @Autowired
    private QuitJobDao quitJobDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ApprovalRecord queryById(Integer id) {
        return this.approvalRecordDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ApprovalRecord> queryAllByLimit(int offset, int limit) {
        return this.approvalRecordDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param approvalRecord 实例对象
     * @return 实例对象
     */
    @Override
    public ApprovalRecord insert(ApprovalRecord approvalRecord) {
        this.approvalRecordDao.insert(approvalRecord);
        return approvalRecord;
    }

    /**
     * 修改数据
     *
     * @param approvalRecord 实例对象
     * @return 实例对象
     */
    @Override
    public ApprovalRecord update(ApprovalRecord approvalRecord) {
        this.approvalRecordDao.update(approvalRecord);
        return this.queryById(approvalRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.approvalRecordDao.deleteById(id) > 0;
    }


    @Override
    public ResultPage getAllMyApproval(JSONObject params) {
        ApprovalRecord approvalRecord = params.toJavaObject(ApprovalRecord.class);
        approvalRecord.buildLimit();

        int userId = UserUtils.getCurrentUserId();
        // type 等于 1 表示当前要查找的是审批别人的记录
        if (params.getInteger("type") == 1) {
            approvalRecord.setApprovalUserId(userId);
        }
        // type 等于 0 表示当前要查找的是自己被别人审批的记录
        if (params.getInteger("type") == 0) {
            approvalRecord.setProduceUserId(userId);
        }

        List<ApprovalRecord> approvalRecords = approvalRecordDao.queryAllPage(approvalRecord);
        Long total = approvalRecordDao.queryAllPageCount(approvalRecord);

        List<ApprovalRecordVo> vos = JSONArray.parseArray(JSONArray.toJSONString(approvalRecords), ApprovalRecordVo.class);
        for (ApprovalRecordVo vo : vos) {
            vo.setProduceUserName(userDao.queryById(vo.getProduceUserId()).getRealName());
            vo.setApprovalUserName(userDao.queryById(vo.getApprovalUserId()).getRealName());
            vo.setApprovalTypeStr(ApprovalTypeEnum.getEnumByCode(vo.getApprovalType()).getDesc());
            vo.setRecordStatusStr(RecordStatusEnum.getEnumByCode(vo.getRecordStatus()).getDesc());
        }
        return Results.createOk(total, vos);
    }

    @Override
    public Result getApprovalDetail(JSONObject params) {
        // 获取审批记录的id
        Integer id = params.getInteger("id");
        if (id == null) return Results.error("审批记录 id 不能为空");
        ApprovalRecord approvalRecord = approvalRecordDao.queryById(id);
        Integer approvalType = approvalRecord.getApprovalType();

        ApprovalTypeEnum approvalTypeEnum = ApprovalTypeEnum.getEnumByCode(approvalType);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        switch (approvalTypeEnum) {
            // 当记录为签到打卡记录时
            case START_REMEDY_SIGN:
                CheckIn startCheckIn = checkInDao.queryById(approvalRecord.getApprovalId());
                Map<String, String> startMap = new LinkedHashMap<>();
                startMap.put("上班打卡时间", startCheckIn.getStartTime().toLocalDateTime().format(formatter));
                // 如果当前状态是待审批，显示要审批的人
                if (RecordStatusEnum.READY_PASS.getCode().equals(approvalRecord.getRecordStatus())) {
                    startMap.put("目前审批人", userDao.queryById(approvalRecord.getApprovalUserId()).getRealName());
                } else {
                    startMap.put("最后审批人", userDao.queryById(approvalRecord.getApprovalUserId()).getRealName());
                }
                return Results.createOk(startMap);
            case END_REMEDY_SIGN:
                CheckIn endCheckIn = checkInDao.queryById(approvalRecord.getApprovalId());
                Map<String, String> endMap = new LinkedHashMap<>();
                endMap.put("下班打卡时间", endCheckIn.getEndTime().toLocalDateTime().format(formatter));
                if (RecordStatusEnum.READY_PASS.getCode().equals(approvalRecord.getRecordStatus())) {
                    endMap.put("目前审批人", userDao.queryById(approvalRecord.getApprovalUserId()).getRealName());
                } else {
                    endMap.put("最后审批人", userDao.queryById(approvalRecord.getApprovalUserId()).getRealName());
                }
                return Results.createOk(endMap);
            case LEAVE:
                Leave leave = leaveDao.queryById(approvalRecord.getApprovalId());
                Map<String, String> leaveMap = new LinkedHashMap<>();
                leaveMap.put("假期类型", HolidayTypeEnum.getEnumByCode(leave.getHolidayType()).getDesc());
                leaveMap.put("假期开始时间", leave.getBeginTime().toLocalDateTime().toLocalDate().toString());
                leaveMap.put("假期结束时间", leave.getEndTime().toLocalDateTime().toLocalDate().toString());
                leaveMap.put("请假原因", leave.getReason());
                if (RecordStatusEnum.READY_PASS.getCode().equals(approvalRecord.getRecordStatus())) {
                    leaveMap.put("目前审批人", userDao.queryById(approvalRecord.getApprovalUserId()).getRealName());
                } else {
                    leaveMap.put("最后审批人", userDao.queryById(approvalRecord.getApprovalUserId()).getRealName());
                }
                return Results.createOk(leaveMap);
            case OVERTIME:
                Overtime overtime = overtimeDao.queryById(approvalRecord.getApprovalId());
                Map<String, String> overtimeMap = new LinkedHashMap<>();
                overtimeMap.put("加班开始时间", overtime.getStartTime().toLocalDateTime().toLocalDate().toString());
                overtimeMap.put("加班结束时间", overtime.getEndTime().toLocalDateTime().toLocalDate().toString());
                overtimeMap.put("加班持续天数", overtime.getContinueDay() + "天");
                overtimeMap.put("加班原因", overtime.getReason());
                if (RecordStatusEnum.READY_PASS.getCode().equals(approvalRecord.getRecordStatus())) {
                    overtimeMap.put("目前审批人", userDao.queryById(approvalRecord.getApprovalUserId()).getRealName());
                } else {
                    overtimeMap.put("最后审批人", userDao.queryById(approvalRecord.getApprovalUserId()).getRealName());
                }
                return Results.createOk(overtimeMap);
            case QUIT_JOB:
                QuitJob quitJob = quitJobDao.queryById(approvalRecord.getApprovalId());
                Map<String, String> quitJobMap = new LinkedHashMap<>();
                quitJobMap.put("离职日期", quitJob.getLeaveTime().toLocalDateTime().toLocalDate().toString());
                quitJobMap.put("离职原因", quitJob.getReason());
                if (RecordStatusEnum.READY_PASS.getCode().equals(approvalRecord.getRecordStatus())) {
                    quitJobMap.put("目前审批人", userDao.queryById(approvalRecord.getApprovalUserId()).getRealName());
                } else {
                    quitJobMap.put("最后审批人", userDao.queryById(approvalRecord.getApprovalUserId()).getRealName());
                }
                return Results.createOk(quitJobMap);
            default:
                break;
        }
        return Results.error("查询记录详情失败");
    }

    @Override
    public Result approvalPass(JSONObject params) {
        Integer id = params.getInteger("id");
        if (id == null) return Results.error("审批记录 id 不能为空");

        ApprovalRecord approvalRecord = approvalRecordDao.queryById(id);

        if (Objects.equals(RecordStatusEnum.getEnumByCode(approvalRecord.getRecordStatus()), RecordStatusEnum.PASSED)
                || Objects.equals(RecordStatusEnum.getEnumByCode(approvalRecord.getRecordStatus()), RecordStatusEnum.NOT_PASSED)) {
            return Results.createOk("该申请已经处理过，无需再次处理");
        }

        ApprovalTypeEnum approvalTypeEnum = ApprovalTypeEnum.getEnumByCode(approvalRecord.getApprovalType());

        switch (approvalTypeEnum) {
            case START_REMEDY_SIGN:
                startRemedySign(approvalRecord);
                return Results.createOk("审批成功");
            case END_REMEDY_SIGN:
                endRemedySign(approvalRecord);
                return Results.createOk("审批成功");
            case LEAVE:
                leave(approvalRecord);
                return Results.createOk("审批成功");
            case OVERTIME:
                overtime(approvalRecord);
                return Results.createOk("审批成功");
            case QUIT_JOB:
                quitJob(approvalRecord);
                return Results.createOk("审批成功");
            default:
                break;
        }
        return Results.error("审批失败");
    }

    private void quitJob(ApprovalRecord approvalRecord) {
        // 申请人申请的记录
        QuitJob oldRecord = quitJobDao.queryById(approvalRecord.getApprovalId());
        Integer produceUserId = approvalRecord.getProduceUserId();
        User user = userDao.queryById(produceUserId);
        Integer departmentId = user.getDepartmentId();
        // 申请人所在部门的审批流
        ApprovalFlow flow = approvalFlowDao.queryByDepId(departmentId);
        // 没有审批流，直接部门主管通过即可
        if (flow == null) {
            // 构造审批通过的新纪录
            quitJobPass(oldRecord, produceUserId, approvalRecord);
        } else {
            int userId = UserUtils.getCurrentUserId();
            Integer first = flow.getFirstApprovalMan();
            Integer second = flow.getSecondApprovalMan();
            Integer third = flow.getThirdApprovalMan();
            // 有第一审批人、无第二第三审批人 说明当前用户是第一审批人，直接通过即可
            if (first != null && second == null && third == null) {
                quitJobPass(oldRecord, produceUserId, approvalRecord);
            }
            // 有第一第二审批人，且无第三审批 这种情况不存在

            // 有第一第三审批人，无第二审批人
            if (first != null && third != null && second == null) {
                // 如果当前是第一审批人，构造第三审批人数据
                if (userId == first) {
                    // 如果第三审批人账号不可用，直接审批通过
                    if (EnableBooleanEnum.DISABLE.getCode().equals(userDao.queryById(third).isEnabled())) {
                        quitJobPass(oldRecord, produceUserId, approvalRecord);
                    } else {
                        leaveApplyNext(approvalRecord, third);
                    }
                } else {
                    // 不然就是第三审批人，直接通过
                    quitJobPass(oldRecord, produceUserId, approvalRecord);
                }
            }
            // 有第一第二第三审批人
            if (first != null && second != null && third != null) {
                if (userId == first) {
                    // 如果第二审批人账号不可用，指派给第三审批人
                    if (EnableBooleanEnum.DISABLE.getCode().equals(userDao.queryById(second).isEnabled())) {
                        // 如果第三审批人账号不可用，直接审批通过
                        if (EnableBooleanEnum.DISABLE.getCode().equals(userDao.queryById(third).isEnabled())) {
                            quitJobPass(oldRecord, produceUserId, approvalRecord);
                        } else {
                            leaveApplyNext(approvalRecord, third);
                        }
                    } else {
                        leaveApplyNext(approvalRecord, second);
                    }
                } else if (userId == second) {
                    // 如果第三审批人账号不可用，直接审批通过
                    if (EnableBooleanEnum.DISABLE.getCode().equals(userDao.queryById(third).isEnabled())) {
                        quitJobPass(oldRecord, produceUserId, approvalRecord);
                    } else {
                        leaveApplyNext(approvalRecord, third);
                    }
                } else {
                    quitJobPass(oldRecord, produceUserId, approvalRecord);
                }
            }
        }
    }

    private void quitJobPass(QuitJob oldRecord, Integer produceUserId, ApprovalRecord approvalRecord) {
        QuitJob newRecord = JSONObject.parseObject(JSONObject.toJSONString(oldRecord), QuitJob.class);
        newRecord.setId(null);
        newRecord.setEnabled(EnableBooleanEnum.ENABLED.getCode());
        newRecord.setCreateTime(DateUtils.getNowTime());
        newRecord.setLeaveTime(new Timestamp(LocalDate.now().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli()));
        quitJobDao.insert(newRecord);

        // 禁用账号
        User user = userDao.queryById(produceUserId);
        user.setEnabled(EnableBooleanEnum.DISABLE.getCode());
        // 离职日期
        user.setDepartureTime(new Timestamp(LocalDate.now().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli()));
        user.setWorkState(WorkStateEnum.OUT.getCode());
        userDao.update(user);

        approvalRecord.setRecordStatus(RecordStatusEnum.PASSED.getCode());
        approvalRecordDao.update(approvalRecord);
    }

    private void overtime(ApprovalRecord approvalRecord) {
        // 申请人申请的记录
        Overtime overtime = overtimeDao.queryById(approvalRecord.getApprovalId());
        Integer produceUserId = approvalRecord.getProduceUserId();
        User user = userDao.queryById(produceUserId);
        Integer departmentId = user.getDepartmentId();
        // 申请人所在部门的审批流
        ApprovalFlow flow = approvalFlowDao.queryByDepId(departmentId);

        // 加班表记录添加
        Overtime newRecord = JSONObject.parseObject(JSONObject.toJSONString(overtime), Overtime.class);
        newRecord.setId(null);
        newRecord.setEnabled(EnableBooleanEnum.ENABLED.getCode());
        newRecord.setCreateTime(DateUtils.getNowTime());
        overtimeDao.insert(newRecord);
        // 假期表，补休添加
        Holiday holiday = new Holiday();
        holiday.setUserId(produceUserId);
        holiday.setHolidayType(HolidayTypeEnum.EXCHANGE.getCode());
        holiday.setCreateTime(String.valueOf(LocalDate.now().getYear()));
        holiday = holidayDao.queryByUserIdAndTypeInCurrentYear(holiday);
        holiday.setHolidayTime(holiday.getHolidayTime() + newRecord.getContinueDay());
        holiday.setRemaining(holiday.getRemaining() + newRecord.getContinueDay());
        holidayDao.update(holiday);
        approvalRecord.setRecordStatus(RecordStatusEnum.PASSED.getCode());
        approvalRecordDao.update(approvalRecord);
    }

    private void leave(ApprovalRecord approvalRecord) {
        // 申请人申请的记录
        Leave oldRecord = leaveDao.queryById(approvalRecord.getApprovalId());
        Integer produceUserId = approvalRecord.getProduceUserId();
        User user = userDao.queryById(produceUserId);
        Integer departmentId = user.getDepartmentId();
        // 申请人所在部门的审批流
        ApprovalFlow flow = approvalFlowDao.queryByDepId(departmentId);
        // 没有审批流，直接部门主管通过即可
        if (flow == null) {
            // 构造审批通过的新纪录
            leaveApplyPass(oldRecord, produceUserId, approvalRecord);
        } else {
            int userId = UserUtils.getCurrentUserId();
            Integer first = flow.getFirstApprovalMan();
            Integer second = flow.getSecondApprovalMan();
            Integer third = flow.getThirdApprovalMan();
            // 有第一审批人、无第二第三审批人 说明当前用户是第一审批人，直接通过即可
            if (first != null && second == null && third == null) {
                leaveApplyPass(oldRecord, produceUserId, approvalRecord);
            }
            // 有第一第二审批人，且无第三审批 这种情况不存在

            // 有第一第三审批人，无第二审批人
            if (first != null && third != null && second == null) {
                // 如果当前是第一审批人，构造第三审批人数据
                if (userId == first) {
                    // 如果第三审批人账号不可用，直接审批通过
                    if (EnableBooleanEnum.DISABLE.getCode().equals(userDao.queryById(third).isEnabled())) {
                        leaveApplyPass(oldRecord, produceUserId, approvalRecord);
                    } else {
                        leaveApplyNext(approvalRecord, third);
                    }
                } else {
                    // 不然就是第三审批人，直接通过
                    leaveApplyPass(oldRecord, produceUserId, approvalRecord);
                }
            }
            // 有第一第二第三审批人
            if (first != null && second != null && third != null) {
                if (userId == first) {
                    // 如果第二审批人账号不可用，指派给第三审批人
                    if (EnableBooleanEnum.DISABLE.getCode().equals(userDao.queryById(second).isEnabled())) {
                        // 如果第三审批人账号不可用，直接审批通过
                        if (EnableBooleanEnum.DISABLE.getCode().equals(userDao.queryById(third).isEnabled())) {
                            leaveApplyPass(oldRecord, produceUserId, approvalRecord);
                        } else {
                            leaveApplyNext(approvalRecord, third);
                        }
                    } else {
                        leaveApplyNext(approvalRecord, second);
                    }
                } else if (userId == second) {
                    // 如果第三审批人账号不可用，直接审批通过
                    if (EnableBooleanEnum.DISABLE.getCode().equals(userDao.queryById(third).isEnabled())) {
                        leaveApplyPass(oldRecord, produceUserId, approvalRecord);
                    } else {
                        leaveApplyNext(approvalRecord, third);
                    }
                } else {
                    leaveApplyPass(oldRecord, produceUserId, approvalRecord);
                }
            }
        }
    }

    /**
     * 请假审批通过
     *
     * @param oldRecord
     * @param produceUserId
     * @param approvalRecord
     */
    private void leaveApplyPass(Leave oldRecord, Integer produceUserId, ApprovalRecord approvalRecord) {
        Leave newRecord = JSONObject.parseObject(JSONObject.toJSONString(oldRecord), Leave.class);
        newRecord.setId(null);
        newRecord.setEnabled(EnableBooleanEnum.ENABLED.getCode());
        newRecord.setCreateTime(DateUtils.getNowTime());
        leaveDao.insert(newRecord);
        // 减少总假期,如果是事假就不用减了
        if (!Objects.equals(HolidayTypeEnum.OTHER, HolidayTypeEnum.getEnumByCode(oldRecord.getHolidayType()))) {
            Holiday holiday = new Holiday();
            holiday.setUserId(produceUserId);
            holiday.setHolidayType(oldRecord.getHolidayType());
            holiday.setCreateTime(String.valueOf(LocalDate.now().getYear()));
            holiday = holidayDao.queryByUserIdAndTypeInCurrentYear(holiday);
            holiday.setRemaining(holiday.getRemaining() - DateUtils
                    .getGapDays(oldRecord.getBeginTime().toLocalDateTime().toLocalDate(), oldRecord.getEndTime().toLocalDateTime().toLocalDate()));
            holidayDao.update(holiday);
        }
        approvalRecord.setRecordStatus(RecordStatusEnum.PASSED.getCode());
        approvalRecordDao.update(approvalRecord);
    }

    /**
     * 构建下一个审批人记录
     */
    private void leaveApplyNext(ApprovalRecord approvalRecord, Integer approvalMan) {
        // 将第二审批人的记录更改为审批完成
        approvalRecord.setRecordStatus(RecordStatusEnum.PASSED.getCode());
        approvalRecordDao.update(approvalRecord);
        // 为第三审批人新增审批记录
        approvalRecord.setId(null);
        approvalRecord.setApprovalUserId(approvalMan);
        approvalRecord.setRecordStatus(RecordStatusEnum.READY_PASS.getCode());
        approvalRecord.setCreateTime(DateUtils.getNowTime());
        approvalRecordDao.insert(approvalRecord);
    }

    private void startRemedySign(ApprovalRecord approvalRecord) {

        // 申请人申请的记录
        CheckIn newRecord = checkInDao.queryById(approvalRecord.getApprovalId());

        // 获取待被更新的记录
        CheckIn checkIn = checkInDao.queryByCreateTime(newRecord.getCreateTime(), approvalRecord.getProduceUserId());

        checkIn.setStartTime(newRecord.getStartTime());
        checkIn.setStartType(newRecord.getStartType());
        if (checkIn.getEndTime() != null) {
            double workHours = DateUtils.getGapTime(checkIn.getStartTime().toLocalDateTime(), checkIn.getEndTime().toLocalDateTime());
            checkIn.setWorkHours(workHours);
            checkIn.setSignType(SignTypeEnum.FULL.getCode());
        } else {
            checkIn.setWorkHours(4.0);
            checkIn.setSignType(SignTypeEnum.HALF.getCode());
        }
        checkInDao.update(checkIn);
        approvalRecord.setRecordStatus(RecordStatusEnum.PASSED.getCode());
        approvalRecordDao.update(approvalRecord);
    }

    private void endRemedySign(ApprovalRecord approvalRecord) {

        // 申请人申请的记录
        CheckIn newRecord = checkInDao.queryById(approvalRecord.getApprovalId());

        // 获取待被更新的记录
        CheckIn checkIn = checkInDao.queryByCreateTime(newRecord.getCreateTime(), approvalRecord.getProduceUserId());

        checkIn.setEndTime(newRecord.getEndTime());
        checkIn.setEndType(newRecord.getEndType());
        checkIn.setSignType(newRecord.getSignType());
        if (checkIn.getStartTime() != null) {
            double workHours = DateUtils.getGapTime(checkIn.getStartTime().toLocalDateTime(), checkIn.getEndTime().toLocalDateTime());
            checkIn.setWorkHours(workHours);
            checkIn.setSignType(SignTypeEnum.FULL.getCode());
        } else {
            checkIn.setWorkHours(4.0);
            checkIn.setSignType(SignTypeEnum.HALF.getCode());
        }
        checkInDao.update(checkIn);
        approvalRecord.setRecordStatus(RecordStatusEnum.PASSED.getCode());
        approvalRecordDao.update(approvalRecord);
    }

    @Override
    public Result notPass(JSONObject params) {
        Integer id = params.getInteger("id");
        if (id == null) return Results.error("审批记录 id 不能为空");
        ApprovalRecord approvalRecord = approvalRecordDao.queryById(id);
        if (Objects.equals(RecordStatusEnum.getEnumByCode(approvalRecord.getRecordStatus()), RecordStatusEnum.PASSED)
                || Objects.equals(RecordStatusEnum.getEnumByCode(approvalRecord.getRecordStatus()), RecordStatusEnum.NOT_PASSED)) {
            return Results.createOk("该申请已经处理过，无需再次处理");
        }
        approvalRecord.setRecordStatus(RecordStatusEnum.NOT_PASSED.getCode());
        approvalRecordDao.update(approvalRecord);
        return Results.createOk("驳回成功");
    }
}