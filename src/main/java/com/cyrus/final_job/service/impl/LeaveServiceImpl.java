package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.*;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.*;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.condition.LeaveCondition;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.entity.vo.LeaveInfoVo;
import com.cyrus.final_job.entity.vo.LeaveVo;
import com.cyrus.final_job.enums.ApprovalTypeEnum;
import com.cyrus.final_job.enums.EnableBooleanEnum;
import com.cyrus.final_job.enums.HolidayTypeEnum;
import com.cyrus.final_job.enums.RecordStatusEnum;
import com.cyrus.final_job.service.LeaveService;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 员工请假表(Leave)表服务实现类
 *
 * @author cyrus
 * @since 2020-03-02 14:58:10
 */
@Service("leaveService")
public class LeaveServiceImpl implements LeaveService {
    @Resource
    private LeaveDao leaveDao;

    @Autowired
    private ApprovalFlowDao approvalFlowDao;

    @Autowired
    private ApprovalRecordDao approvalRecordDao;

    @Autowired
    private HolidayDao holidayDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Leave queryById(Integer id) {
        return this.leaveDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Leave> queryAllByLimit(int offset, int limit) {
        return this.leaveDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param leave 实例对象
     * @return 实例对象
     */
    @Override
    public Leave insert(Leave leave) {
        this.leaveDao.insert(leave);
        return leave;
    }

    /**
     * 修改数据
     *
     * @param leave 实例对象
     * @return 实例对象
     */
    @Override
    public Leave update(Leave leave) {
        this.leaveDao.update(leave);
        return this.queryById(leave.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.leaveDao.deleteById(id) > 0;
    }

    @Override
    public Result leaveApply(JSONObject params) {
        Leave leave = params.toJavaObject(Leave.class);
        Result result = leave.checkAndBuildParams();
        if (result != null) return result;

        // 如果是事假就不用判斷假期余额了
        if (!Objects.equals(HolidayTypeEnum.OTHER, HolidayTypeEnum.getEnumByCode(leave.getHolidayType()))) {
            Holiday holiday = new Holiday();
            holiday.setHolidayType(leave.getHolidayType());
            holiday.setUserId(UserUtils.getCurrentUserId());
            holiday.setCreateTime(String.valueOf(LocalDate.now().getYear()));
            holiday = holidayDao.queryByUserIdAndTypeInCurrentYear(holiday);
            Integer gapDays = DateUtils.getGapDays(leave.getBeginTime().toLocalDateTime().toLocalDate(),
                    leave.getEndTime().toLocalDateTime().toLocalDate());
            if (gapDays > holiday.getRemaining()) {
                return Results.error("假期余额不足");
            }
        }

        if (leave.getBeginTime().toLocalDateTime().toLocalDate().isBefore(LocalDate.now())) {
            return Results.error("请假时间不得早于现在");
        }

        Leave user = new Leave();
        user.setUserId(UserUtils.getCurrentUserId());
        user.setEnabled(true);
        List<Leave> leaves = leaveDao.queryAll(user);
        for (Leave leaf : leaves) {
            Boolean aBoolean = DateUtils.TimeRepeatJudge(leaf.getBeginTime(), leaf.getEndTime(), leave.getBeginTime(), leave.getEndTime());
            if (aBoolean) {
                return Results.error("假期范围不能重复");
            }
        }


        leaveDao.insert(leave);

        User currentUser = UserUtils.getCurrentUser();
        Integer departmentId = currentUser.getDepartmentId();
        ApprovalFlow flow = approvalFlowDao.queryByDepId(departmentId);

        ApprovalRecord approvalRecord = new ApprovalRecord();
        approvalRecord.setProduceUserId(currentUser.getId());
        approvalRecord.setApprovalType(ApprovalTypeEnum.LEAVE.getCode());

        Integer firstApprovalMan = null;
        if (flow == null) {
            Department department = departmentDao.queryById(departmentId);
            firstApprovalMan = department.getUserId();
        } else {
            if (EnableBooleanEnum.DISABLE.getCode().equals(userDao.queryById(flow.getFirstApprovalMan()).isEnabled())) {
                Department department = departmentDao.queryById(departmentId);
                firstApprovalMan = department.getUserId();
            } else {
                firstApprovalMan = flow.getFirstApprovalMan();
            }
        }

        approvalRecord.setApprovalUserId(firstApprovalMan);
        approvalRecord.setRecordStatus(RecordStatusEnum.READY_PASS.getCode());
        approvalRecord.setApprovalId(leave.getId());
        approvalRecord.setCreateTime(DateUtils.getNowTime());
        approvalRecordDao.insert(approvalRecord);
        return Results.createOk("申请成功");
    }

    @Override
    public ResultPage getMyAppliedHolidays(JSONObject params) {
        int userId = UserUtils.getCurrentUserId();
        LeaveCondition leaveCondition = params.toJavaObject(LeaveCondition.class);
        leaveCondition.buildLimit();
        leaveCondition.setUserId(userId);
        List<Leave> leaves = leaveDao.queryAllByCondition(leaveCondition);
        Long total = leaveDao.queryAllByConditionCount(leaveCondition);
        List<LeaveVo> leaveVos = JSONArray.parseArray(JSONArray.toJSONString(leaves), LeaveVo.class);
        for (LeaveVo leaveVo : leaveVos) {
            leaveVo.setRealName(userDao.queryById(leaveVo.getUserId()).getRealName());
            leaveVo.setHolidayTypeStr(HolidayTypeEnum.getEnumByCode(leaveVo.getHolidayType()).getDesc());
        }
        return Results.createOk(total, leaveVos);
    }

    @Override
    public Result getLeaveInfo(JSONObject params) {
        Timestamp month = params.getObject("month", Timestamp.class);
        int userId = UserUtils.getCurrentUserId();
        Timestamp start = null;
        Timestamp end = null;
        if (Objects.isNull(month)) {
            start = DateUtils.LocalDate2Timestamp(DateUtils.getCurrentMonthFirstDay());
            end = DateUtils.LocalDate2Timestamp(DateUtils.getCurrentMonthLasterDay());
        } else {
            start = DateUtils.LocalDate2Timestamp(DateUtils.getMonthFirstDay(month.toLocalDateTime().toLocalDate()));
            end = DateUtils.LocalDate2Timestamp(DateUtils.getMonthLasteDay(month.toLocalDateTime().toLocalDate()));
        }
        List<Leave> leaveInfo = leaveDao.queryLeaveInfo(userId, start, end);
        List<LeaveInfoVo> leaveInfoVos = new ArrayList<>();
        for (Leave leave : leaveInfo) {
            LeaveInfoVo leaveInfoVo = new LeaveInfoVo();
            leaveInfoVo.setLeaveType(HolidayTypeEnum.getEnumByCode(leave.getHolidayType()).getDesc());
            String startTime = leave.getBeginTime().toLocalDateTime().toLocalDate().toString();
            String endTime = leave.getEndTime().toLocalDateTime().toLocalDate().toString();
            leaveInfoVo.setTime(startTime + " 到 " + endTime);
            leaveInfoVos.add(leaveInfoVo);
        }
        return Results.createOk(leaveInfoVos);
    }
}