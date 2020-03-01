package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.ApprovalRecordDao;
import com.cyrus.final_job.dao.CheckInDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.ApprovalRecord;
import com.cyrus.final_job.entity.CheckIn;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.vo.ApprovalRecordVo;
import com.cyrus.final_job.enums.ApprovalTypeEnum;
import com.cyrus.final_job.enums.RecordStatusEnum;
import com.cyrus.final_job.enums.SignTypeEnum;
import com.cyrus.final_job.service.ApprovalRecordService;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
                return Results.createOk(startMap);
            case END_REMEDY_SIGN:
                CheckIn endCheckIn = checkInDao.queryById(approvalRecord.getApprovalId());
                Map<String, String> endMap = new LinkedHashMap<>();
                endMap.put("下班打卡时间", endCheckIn.getEndTime().toLocalDateTime().format(formatter));
                return Results.createOk(endMap);
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
            default:
                break;
        }
        return Results.error("审批失败");
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