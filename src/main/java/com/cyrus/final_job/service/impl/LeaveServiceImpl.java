package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.*;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.*;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.enums.ApprovalTypeEnum;
import com.cyrus.final_job.enums.EnableBooleanEnum;
import com.cyrus.final_job.enums.RecordStatusEnum;
import com.cyrus.final_job.service.LeaveService;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

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

        if (leave.getBeginTime().toLocalDateTime().toLocalDate().isBefore(LocalDate.now())) {
            return Results.error("请假时间不得早于现在");
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
}