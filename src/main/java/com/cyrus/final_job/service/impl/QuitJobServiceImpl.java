package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.ApprovalFlowDao;
import com.cyrus.final_job.dao.ApprovalRecordDao;
import com.cyrus.final_job.dao.DepartmentDao;
import com.cyrus.final_job.dao.QuitJobDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.ApprovalFlow;
import com.cyrus.final_job.entity.ApprovalRecord;
import com.cyrus.final_job.entity.Department;
import com.cyrus.final_job.entity.QuitJob;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.enums.ApprovalTypeEnum;
import com.cyrus.final_job.enums.EnableBooleanEnum;
import com.cyrus.final_job.enums.RecordStatusEnum;
import com.cyrus.final_job.service.QuitJobService;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 离职表(QuitJob)表服务实现类
 *
 * @author cyrus
 * @since 2020-03-09 09:52:07
 */
@Service("quitJobService")
public class QuitJobServiceImpl implements QuitJobService {
    @Resource
    private QuitJobDao quitJobDao;

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
    public QuitJob queryById(Integer id) {
        return this.quitJobDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<QuitJob> queryAllByLimit(int offset, int limit) {
        return this.quitJobDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param quitJob 实例对象
     * @return 实例对象
     */
    @Override
    public QuitJob insert(QuitJob quitJob) {
        this.quitJobDao.insert(quitJob);
        return quitJob;
    }

    /**
     * 修改数据
     *
     * @param quitJob 实例对象
     * @return 实例对象
     */
    @Override
    public QuitJob update(QuitJob quitJob) {
        this.quitJobDao.update(quitJob);
        return this.queryById(quitJob.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.quitJobDao.deleteById(id) > 0;
    }

    @Override
    public Result quitJobApply(JSONObject params) {
        QuitJob quitJob = params.toJavaObject(QuitJob.class);
        Result result = quitJob.checkAndBuildParams();
        if (result != null) return result;
        quitJobDao.insert(quitJob);

        User currentUser = UserUtils.getCurrentUser();
        Integer departmentId = currentUser.getDepartmentId();
        ApprovalFlow flow = approvalFlowDao.queryByDepId(departmentId);

        ApprovalRecord approvalRecord = new ApprovalRecord();
        approvalRecord.setProduceUserId(currentUser.getId());
        approvalRecord.setApprovalType(ApprovalTypeEnum.QUIT_JOB.getCode());

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
        approvalRecord.setApprovalId(quitJob.getId());
        approvalRecord.setCreateTime(DateUtils.getNowTime());
        approvalRecordDao.insert(approvalRecord);
        return Results.createOk("申请成功，待审批");
    }
}