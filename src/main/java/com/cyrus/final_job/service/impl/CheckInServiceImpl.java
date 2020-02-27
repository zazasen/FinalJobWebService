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
import com.cyrus.final_job.entity.vo.SignCalendarVo;
import com.cyrus.final_job.enums.*;
import com.cyrus.final_job.service.CheckInService;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
                // 该天未签到
                SignCalendarVo vo = new SignCalendarVo();
                vo.setDate(s);
                vo.setStatus("未签到");
                list.add(vo);
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
        condition.setUserId(UserUtils.getCurrentUserId());
        List<CheckInRecordVo> vos = buildAttendanceRecord(condition);
        Long total = checkInDao.queryAllByConditionCount(condition);
        return Results.createOk(total, vos);
    }

    @Override
    public ResultPage getAllAttendanceRecord(JSONObject params) {
        CheckInCondition condition = params.toJavaObject(CheckInCondition.class);
        condition.buildLimit();
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
                startApplySign(condition);
                return Results.createOk("已发起申请");
            case END_SIGN_APPLY:
                endApplySign(condition);
                return Results.createOk("已发起申请");
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
    private void endApplySign(RemedySignCondition condition) {
        ApprovalRecord approvalRecord = new ApprovalRecord();
        User user = userDao.queryById(condition.getUserId());
        // 要审批的人，这里补卡都是只要直接主管审批即可
        Integer managerId = departmentDao.queryById(user.getDepartmentId()).getUserId();
        // 申请人
        approvalRecord.setProduceUserId(user.getId());
        approvalRecord.setApprovalType(ApprovalTypeEnum.END_REMEDY_SIGN.getCode());
        approvalRecord.setApprovalUserId(managerId);
        approvalRecord.setRecordStatus(RecordStatusEnum.READY_PASS.getCode());
        LocalDate createTime = condition.getEndTime().toLocalDateTime().toLocalDate();
        // 根据要补卡的日期和申请人查询是否已经有记录，有记录则更新该条记录，没记录就创建记录
        CheckIn applyRecord = checkInDao.queryApplyRecordByCreateTime(createTime.toString(), condition.getUserId());
        if (applyRecord == null) {
            CheckIn checkIn = new CheckIn();
            checkIn.setUserId(condition.getUserId());
            checkIn.setEndTime(condition.getEndTime());
            checkIn.setEndType(SignInTypeEnum.REMEDY_SIGN.getCode());
            checkIn.setCreateTime(createTime.toString());
            checkIn.setEnabled(EnableBooleanEnum.DISABLE.getCode());
            checkIn.setSignType(SignTypeEnum.HALF.getCode());
            checkIn.setWorkHours(4.0);
            checkInDao.insert(checkIn);
            approvalRecord.setApprovalId(checkIn.getId());
        } else {
            applyRecord.setEndTime(condition.getEndTime());
            applyRecord.setEndType(SignInTypeEnum.REMEDY_SIGN.getCode());
            applyRecord.setSignType(SignTypeEnum.FULL.getCode());
            double workHours = DateUtils.getGapTime(applyRecord.getStartTime().toLocalDateTime(), condition.getEndTime().toLocalDateTime());
            applyRecord.setWorkHours(workHours);
            checkInDao.update(applyRecord);
            approvalRecord.setApprovalId(applyRecord.getId());
        }
        approvalRecordDao.insert(approvalRecord);
    }

    /**
     * 补卡类型为签到补卡
     *
     * @param condition
     */
    private void startApplySign(RemedySignCondition condition) {
        ApprovalRecord approvalRecord = new ApprovalRecord();
        User user = userDao.queryById(condition.getUserId());
        Integer managerId = departmentDao.queryById(user.getDepartmentId()).getUserId();
        approvalRecord.setProduceUserId(user.getId());
        approvalRecord.setApprovalType(ApprovalTypeEnum.START_REMEDY_SIGN.getCode());
        approvalRecord.setApprovalUserId(managerId);
        approvalRecord.setRecordStatus(RecordStatusEnum.READY_PASS.getCode());
        LocalDate createTime = condition.getStartTime().toLocalDateTime().toLocalDate();
        // 根据要补卡的日期和申请人查询是否已经有记录，有记录则更新该条记录，没记录就创建记录
        CheckIn applyRecord = checkInDao.queryApplyRecordByCreateTime(createTime.toString(), condition.getUserId());
        if (applyRecord == null) {
            CheckIn checkIn = new CheckIn();
            checkIn.setUserId(condition.getUserId());
            checkIn.setStartTime(condition.getStartTime());
            checkIn.setStartType(SignInTypeEnum.REMEDY_SIGN.getCode());
            checkIn.setCreateTime(createTime.toString());
            checkIn.setEnabled(EnableBooleanEnum.DISABLE.getCode());
            checkIn.setSignType(SignTypeEnum.HALF.getCode());
            checkIn.setWorkHours(4.0);
            checkInDao.insert(checkIn);
            approvalRecord.setApprovalId(checkIn.getId());
        } else {
            applyRecord.setStartTime(condition.getStartTime());
            applyRecord.setStartType(SignInTypeEnum.REMEDY_SIGN.getCode());
            applyRecord.setSignType(SignTypeEnum.FULL.getCode());
            double workHours = DateUtils.getGapTime(condition.getStartTime().toLocalDateTime(), applyRecord.getEndTime().toLocalDateTime());
            applyRecord.setWorkHours(workHours);
            checkInDao.update(applyRecord);
            approvalRecord.setApprovalId(applyRecord.getId());
        }
        approvalRecordDao.insert(approvalRecord);
    }
}