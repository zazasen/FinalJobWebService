package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.ApprovalFlowDao;
import com.cyrus.final_job.dao.ApprovalRecordDao;
import com.cyrus.final_job.dao.DepartmentDao;
import com.cyrus.final_job.dao.OvertimeDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.ApprovalFlow;
import com.cyrus.final_job.entity.ApprovalRecord;
import com.cyrus.final_job.entity.Department;
import com.cyrus.final_job.entity.Overtime;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.enums.ApprovalTypeEnum;
import com.cyrus.final_job.enums.EnableBooleanEnum;
import com.cyrus.final_job.enums.RecordStatusEnum;
import com.cyrus.final_job.service.OvertimeService;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 员工加班申请(Overtime)表服务实现类
 *
 * @author cyrus
 * @since 2020-03-04 14:57:13
 */
@Service("overtimeService")
public class OvertimeServiceImpl implements OvertimeService {
    @Resource
    private OvertimeDao overtimeDao;

    @Autowired
    private ApprovalFlowDao approvalFlowDao;
    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ApprovalRecordDao approvalRecordDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Overtime queryById(Integer id) {
        return this.overtimeDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Overtime> queryAllByLimit(int offset, int limit) {
        return this.overtimeDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param overtime 实例对象
     * @return 实例对象
     */
    @Override
    public Overtime insert(Overtime overtime) {
        this.overtimeDao.insert(overtime);
        return overtime;
    }

    /**
     * 修改数据
     *
     * @param overtime 实例对象
     * @return 实例对象
     */
    @Override
    public Overtime update(Overtime overtime) {
        this.overtimeDao.update(overtime);
        return this.queryById(overtime.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.overtimeDao.deleteById(id) > 0;
    }

    @Override
    public Result overtimeApply(JSONObject params) {
        Overtime overtime = params.toJavaObject(Overtime.class);
        if (overtime.getStartTime() == null || overtime.getEndTime() == null) {
            return Results.error("加班时间段不能为空");
        }
        if (overtime.getReason() == null) {
            return Results.error("加班原因不能为空");
        }
        overtime.setEnabled(EnableBooleanEnum.DISABLE.getCode());
        Integer days = DateUtils.getGapDays(overtime.getStartTime().toLocalDateTime().toLocalDate(),
                overtime.getEndTime().toLocalDateTime().toLocalDate());
        overtime.setContinueDay(days);
        overtime.setCreateTime(DateUtils.getNowTime());
        overtime.setUserId(UserUtils.getCurrentUserId());

        overtimeDao.insert(overtime);

        ApprovalRecord approvalRecord = new ApprovalRecord();
        approvalRecord.setProduceUserId(UserUtils.getCurrentUserId());
        approvalRecord.setApprovalType(ApprovalTypeEnum.OVERTIME.getCode());

        User currentUser = UserUtils.getCurrentUser();
        Integer departmentId = currentUser.getDepartmentId();
        ApprovalFlow flow = approvalFlowDao.queryByDepId(departmentId);
        Integer approvalMan = null;
        if (flow == null) {
            Department department = departmentDao.queryById(departmentId);
            approvalMan = department.getUserId();
        } else {
            if (EnableBooleanEnum.DISABLE.getCode().equals(userDao.queryById(flow.getFirstApprovalMan()).isEnabled())) {
                Department department = departmentDao.queryById(departmentId);
                approvalMan = department.getUserId();
            } else {
                approvalMan = flow.getFirstApprovalMan();
            }
        }
        approvalRecord.setApprovalUserId(approvalMan);
        approvalRecord.setRecordStatus(RecordStatusEnum.READY_PASS.getCode());
        approvalRecord.setApprovalId(overtime.getId());
        approvalRecord.setCreateTime(DateUtils.getNowTime());
        approvalRecordDao.insert(approvalRecord);
        return Results.createOk("申请成功");
    }
}