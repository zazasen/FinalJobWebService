package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.ApprovalFlowDao;
import com.cyrus.final_job.dao.ApprovalRecordDao;
import com.cyrus.final_job.dao.DepartmentDao;
import com.cyrus.final_job.dao.ToFormalDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.ApprovalFlow;
import com.cyrus.final_job.entity.ApprovalRecord;
import com.cyrus.final_job.entity.Department;
import com.cyrus.final_job.entity.ToFormal;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.enums.ApprovalTypeEnum;
import com.cyrus.final_job.enums.EnableBooleanEnum;
import com.cyrus.final_job.enums.RecordStatusEnum;
import com.cyrus.final_job.service.ToFormalService;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * 转正表(ToFormal)表服务实现类
 *
 * @author cyrus
 * @since 2020-04-07 14:37:11
 */
@Service("toFormalService")
public class ToFormalServiceImpl implements ToFormalService {

    @Resource
    private ToFormalDao toFormalDao;

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
    public ToFormal queryById(Integer id) {
        return this.toFormalDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ToFormal> queryAllByLimit(int offset, int limit) {
        return this.toFormalDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param toFormal 实例对象
     * @return 实例对象
     */
    @Override
    public ToFormal insert(ToFormal toFormal) {
        this.toFormalDao.insert(toFormal);
        return toFormal;
    }

    /**
     * 修改数据
     *
     * @param toFormal 实例对象
     * @return 实例对象
     */
    @Override
    public ToFormal update(ToFormal toFormal) {
        this.toFormalDao.update(toFormal);
        return this.queryById(toFormal.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.toFormalDao.deleteById(id) > 0;
    }

    @Override
    public Result conversionApply(JSONObject params) {
        ToFormal toFormal = params.toJavaObject(ToFormal.class);
        Result result = toFormal.checkAndBuildParams();
        if (result != null) return result;
        User user = userDao.queryById(UserUtils.getCurrentUserId());
        if (!Objects.isNull(user.getConversionTime())) {
            return Results.error("您已转正过，无需再次转正");
        }

        if (user.getCreateTime().toLocalDateTime().toLocalDate().isAfter(LocalDate.now().minusMonths(3))) {
            return Results.error("试用期3个月哦");
        }

        toFormalDao.insert(toFormal);

        User currentUser = UserUtils.getCurrentUser();
        Integer departmentId = currentUser.getDepartmentId();
        ApprovalFlow flow = approvalFlowDao.queryByDepId(departmentId);
        ApprovalRecord approvalRecord = new ApprovalRecord();
        approvalRecord.setProduceUserId(currentUser.getId());
        approvalRecord.setApprovalType(ApprovalTypeEnum.CONVERSION.getCode());

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
        approvalRecord.setApprovalId(toFormal.getId());
        approvalRecord.setCreateTime(DateUtils.getNowTime());
        approvalRecordDao.insert(approvalRecord);
        return Results.createOk("申请成功，待审批");
    }
}