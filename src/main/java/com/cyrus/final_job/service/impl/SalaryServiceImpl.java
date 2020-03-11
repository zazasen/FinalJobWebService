package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.*;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.*;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.condition.CheckInCondition;
import com.cyrus.final_job.entity.condition.SalaryCondition;
import com.cyrus.final_job.entity.condition.SalaryEditCondition;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.entity.vo.SalaryVo;
import com.cyrus.final_job.enums.HolidayTypeEnum;
import com.cyrus.final_job.enums.SignTypeEnum;
import com.cyrus.final_job.service.SalaryService;
import com.cyrus.final_job.utils.CommonUtils;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * 员工账套表(Salary)表服务实现类
 *
 * @author cyrus
 * @since 2020-03-10 13:51:03
 */
@Service("salaryService")
public class SalaryServiceImpl implements SalaryService {
    @Resource
    private SalaryDao salaryDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAccountSetDao userAccountSetDao;

    @Autowired
    private AccountSetDao accountSetDao;

    @Autowired
    private CheckInDao checkInDao;

    @Autowired
    private LeaveDao leaveDao;

    @Autowired
    private RewardAndPunishDao rewardAndPunishDao;

    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Salary queryById(Integer id) {
        return this.salaryDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Salary> queryAllByLimit(int offset, int limit) {
        return this.salaryDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param salary 实例对象
     * @return 实例对象
     */
    @Override
    public Salary insert(Salary salary) {
        this.salaryDao.insert(salary);
        return salary;
    }

    /**
     * 修改数据
     *
     * @param salary 实例对象
     * @return 实例对象
     */
    @Override
    public Salary update(Salary salary) {
        this.salaryDao.update(salary);
        return this.queryById(salary.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.salaryDao.deleteById(id) > 0;
    }

    @Override
    public Result salaryHandle() {
        Salary queryItem = new Salary();
        // todo
        queryItem.setCreateTime(new Timestamp(DateUtils.getCurrentMonthFirstDay().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli()));
        List<Salary> judge = salaryDao.queryAll(queryItem);
        if (!CollectionUtils.isEmpty(judge)) {
            return Results.error("生成失败，上月薪资已经生成过");
        }

        User user = new User();
        user.setEnabled(true);
        List<User> users = userDao.queryAll(user);
        List<User> leaveUsers = userDao.queryLeave(DateUtils.getCurrentMonthFirstDay(), DateUtils.getCurrentMonthLasterDay());
        users.addAll(leaveUsers);
        for (User item : users) {
            UserAccountSet temp = userAccountSetDao.queryByUserId(item.getId());
            if (Objects.isNull(temp)) continue;
            // 员工账套
            AccountSet accountSet = accountSetDao.queryById(temp.getAccountSetId());

            CheckInCondition condition = new CheckInCondition();
            condition.setUserId(item.getId());
            // todo
            condition.setBeginTime(DateUtils.getCurrentMonthFirstDay().toString());
            condition.setTailTime(DateUtils.getCurrentMonthLasterDay().toString());
            // 考勤记录
            List<CheckIn> list = checkInDao.queryAllByConditionNoPage(condition);

            Leave leave = new Leave();
            leave.setUserId(item.getId());
            leave.setEnabled(true);
            // 员工假期
            List<Leave> leaves = leaveDao.queryAll(leave);

            // 有效工作日
            int workDays = 0;
            // 带薪休假日
            int leaveDays = 0;
            // 有效工作日的工作时长
            double workedTime = 0;
            // 遍历每天考勤
            for (CheckIn check : list) {
                // 获取每天考勤的工时
                if (!Objects.isNull(check.getWorkHours())) {
                    workedTime = workedTime + check.getWorkHours();
                }
                LocalDate parse = LocalDate.parse(check.getCreateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                // 法定节假日打卡无效
                if (CommonUtils.FreeDayJudge(parse)) {
                    continue;
                }
                // 正常打卡有效
                if (SignTypeEnum.HALF.getCode().equals(check.getSignType()) || SignTypeEnum.FULL.getCode().equals(check.getSignType())) {
                    workDays++;
                    continue;
                }
                LocalDate localDate = LocalDate.parse(check.getCreateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                // 假期打卡，分为带薪假期和不带薪事假
                if (SignTypeEnum.FREE.getCode().equals(check.getSignType())) {
                    for (Leave leaf : leaves) {
                        LocalDate begin = leaf.getBeginTime().toLocalDateTime().toLocalDate();
                        LocalDate end = leaf.getEndTime().toLocalDateTime().toLocalDate();
                        // 如果在假期范围内
                        if ((localDate.isBefore(end) || localDate.isEqual(end)) &&
                                (localDate.isAfter(begin) || localDate.isEqual(begin))) {
                            // 如果该天为假期打卡，并且不是事假，那么有效打卡加一
                            if (!HolidayTypeEnum.getEnumByCode(leaf.getHolidayType()).equals(HolidayTypeEnum.OTHER)) {
                                leaveDays++;
                            }
                            break;
                        }
                    }
                }
            }

            // 奖惩
            List<RewardAndPunish> rewardAndPunishList = rewardAndPunishDao.queryByUserIdAndCreateTime(item.getId(),
                    DateUtils.getCurrentMonthFirstDay(),
                    DateUtils.getCurrentMonthLasterDay());

            // 奖惩金额
            double rewardAndPunishMoney = 0;
            if (!CollectionUtils.isEmpty(rewardAndPunishList)) {
                for (RewardAndPunish rewardAndPunish : rewardAndPunishList) {
                    rewardAndPunishMoney = rewardAndPunish.getMoney();
                }
            }

            double fiveAndOne = accountSet.getPensionBasic() * accountSet.getPensionRatio()
                    + accountSet.getMedicareBenefitsBasic() * accountSet.getMedicareBenefitsRatio()
                    + accountSet.getIndustrialInsuranceBasic() * accountSet.getIndustrialInsuranceRatio()
                    + accountSet.getBusinessInsuranceBasic() * accountSet.getBusinessInsuranceRatio()
                    + accountSet.getBirthInsuranceBasic() * accountSet.getBirthInsuranceRatio()
                    + accountSet.getHousingFundBasic() * accountSet.getHousingFundRatio();

            Salary salary = new Salary();
            salary.setUserId(item.getId());
            salary.setBasicSalary(accountSet.getBasicSalary());

            // 每月要出勤的天数
            Integer days = CommonUtils.shouldBeWorkDays();
            // 出勤天数够了
            if ((workDays + leaveDays) >= days) {
                BigDecimal workTime = new BigDecimal(workedTime);
                BigDecimal shouldTime = new BigDecimal(days * 8.0);
                // 工时也够了
                if (workTime.compareTo(shouldTime) >= 0) {
                    salary.setTrafficAllowance(accountSet.getTrafficAllowance());
                    salary.setPhoneAllowance(accountSet.getPhoneAllowance());
                    salary.setFoodAllowance(accountSet.getFoodAllowance());
                    salary.setTaxes(0 - accountSet.getTaxes());
                    salary.setFiveAndOne(0 - fiveAndOne);
                    salary.setCheckInMoney(0.0);
                    salary.setCheckInReason("无");
                    salary.setRewardAndPunishMoney(rewardAndPunishMoney);
                    salary.setFinalSalary(accountSet.getFinalSalary() + rewardAndPunishMoney);
                    salary.setCreateTime(new Timestamp(DateUtils.getCurrentMonthFirstDay().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli()));
                    salary.setOtherMoney(0.0);
                    salary.setOtherMoneyReason("无");
                    salaryDao.insert(salary);
                    continue;
                } else {
                    // 工时不够
                    double radio = workedTime / (days * 8.0);
                    BigDecimal b = new BigDecimal(radio);
                    radio = b.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
                    salary.setTrafficAllowance(accountSet.getTrafficAllowance() * radio);
                    salary.setPhoneAllowance(accountSet.getPhoneAllowance() * radio);
                    salary.setFoodAllowance(accountSet.getFoodAllowance() * radio);
                    salary.setTaxes(0 - accountSet.getTaxes() * radio);
                    salary.setFiveAndOne(0 - fiveAndOne * radio);
                    salary.setCheckInMoney(accountSet.getFinalSalary() * radio - accountSet.getFinalSalary());
                    salary.setCheckInReason("考勤天数够了，但是工时不够");
                    salary.setRewardAndPunishMoney(rewardAndPunishMoney);
                    salary.setFinalSalary(accountSet.getFinalSalary() * radio + rewardAndPunishMoney);
                    salary.setCreateTime(new Timestamp(DateUtils.getCurrentMonthFirstDay().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli()));
                    salary.setOtherMoney(0.0);
                    salary.setOtherMoneyReason("无");
                    salaryDao.insert(salary);
                    continue;
                }
            } else {
                // 出勤天数不够
                BigDecimal wokeTime = new BigDecimal(workedTime);
                BigDecimal shouldTime = new BigDecimal(workDays * 8.0);
                // 已出勤天数的工时够了
                if (wokeTime.compareTo(shouldTime) >= 0) {
                    double radio = (workDays + leaveDays) / Double.valueOf(days);
                    BigDecimal b = new BigDecimal(radio);
                    radio = b.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
                    salary.setTrafficAllowance(accountSet.getTrafficAllowance() * radio);
                    salary.setPhoneAllowance(accountSet.getPhoneAllowance() * radio);
                    salary.setFoodAllowance(accountSet.getFoodAllowance() * radio);
                    salary.setTaxes(0 - accountSet.getTaxes() * radio);
                    salary.setFiveAndOne(0 - fiveAndOne * radio);
                    salary.setCheckInMoney(accountSet.getFinalSalary() * radio - accountSet.getFinalSalary());
                    salary.setCheckInReason("未满勤");
                    salary.setRewardAndPunishMoney(rewardAndPunishMoney);
                    salary.setFinalSalary(accountSet.getFinalSalary() * radio + rewardAndPunishMoney);
                    salary.setCreateTime(new Timestamp(DateUtils.getCurrentMonthFirstDay().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli()));
                    salary.setOtherMoney(0.0);
                    salary.setOtherMoneyReason("无");
                    salaryDao.insert(salary);
                    continue;
                } else {
                    // 工时不够
                    double timeRadio = workedTime / (workDays * 8.0);
                    BigDecimal b = new BigDecimal(timeRadio);
                    timeRadio = b.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
                    double dayRadio = (workDays + leaveDays) / Double.valueOf(days);
                    BigDecimal b1 = new BigDecimal(dayRadio);
                    dayRadio = b1.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
                    salary.setTrafficAllowance(accountSet.getTrafficAllowance() * dayRadio * timeRadio);
                    salary.setPhoneAllowance(accountSet.getPhoneAllowance() * dayRadio * timeRadio);
                    salary.setFoodAllowance(accountSet.getFoodAllowance() * dayRadio * timeRadio);
                    salary.setTaxes(0 - accountSet.getTaxes() * dayRadio * timeRadio);
                    salary.setFiveAndOne(0 - fiveAndOne * dayRadio * timeRadio);
                    salary.setCheckInMoney(accountSet.getFinalSalary() * dayRadio * timeRadio - accountSet.getFinalSalary());
                    salary.setCheckInReason("未满勤并且工时也不够");
                    salary.setRewardAndPunishMoney(rewardAndPunishMoney);
                    salary.setFinalSalary(accountSet.getFinalSalary() * dayRadio * timeRadio + rewardAndPunishMoney);
                    salary.setCreateTime(new Timestamp(DateUtils.getCurrentMonthFirstDay().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli()));
                    salary.setOtherMoney(0.0);
                    salary.setOtherMoneyReason("无");
                    salaryDao.insert(salary);
                    continue;
                }
            }

        }
        return Results.createOk("上月薪资生成完毕");
    }

    @Override
    public ResultPage getAllSalary(JSONObject params) {
        SalaryCondition condition = params.toJavaObject(SalaryCondition.class);
        condition.buildLimit();
        List<SalaryVo> salaryVos = salaryDao.queryWithUserByCondition(condition);
        for (SalaryVo salaryVo : salaryVos) {
            salaryVo.setDepartmentName(departmentDao.queryById(salaryVo.getDepartmentId()).getName());
            List<RewardAndPunish> list = rewardAndPunishDao.queryByUserIdAndCreateTime(salaryVo.getUserId(),
                    DateUtils.getCurrentMonthFirstDay(),
                    DateUtils.getCurrentMonthLasterDay());
            salaryVo.setRewardAndPunishes(list);
        }
        Long total = salaryDao.queryWithUserByConditionCount(condition);
        return Results.createOk(total, salaryVos);
    }

    @Override
    public ResultPage getMySalary(JSONObject params) {
        SalaryCondition condition = params.toJavaObject(SalaryCondition.class);
        condition.buildLimit();
        condition.setUserId(UserUtils.getCurrentUserId());
        List<Salary> salary = salaryDao.queryOneByCondition(condition);
        List<SalaryVo> salaryVos = JSONArray.parseArray(JSONArray.toJSONString(salary), SalaryVo.class);
        for (SalaryVo salaryVo : salaryVos) {
            List<RewardAndPunish> list = rewardAndPunishDao.queryByUserIdAndCreateTime(salaryVo.getUserId(),
                    DateUtils.getCurrentMonthFirstDay(),
                    DateUtils.getCurrentMonthLasterDay());
            salaryVo.setRewardAndPunishes(list);
        }
        Long total = salaryDao.queryOneByConditionCount(condition);
        return Results.createOk(total, salaryVos);
    }

    @Override
    public Result editSalary(JSONObject params) {
        SalaryEditCondition condition = params.toJavaObject(SalaryEditCondition.class);
        Result result = condition.checkParams();
        if (result != null) return result;
        Salary salary = salaryDao.querySalaryByUserIdAndTime(condition.getUserId(), condition.getCreateTime().toLocalDateTime().toLocalDate());
        if (!Objects.isNull(salary)) {
            salary.setOtherMoney(condition.getMoney());
            salary.setOtherMoneyReason(condition.getReason());
            salary.setFinalSalary(salary.getFinalSalary() + condition.getMoney());
            salaryDao.update(salary);
            return Results.createOk("补差价成功");
        } else {
            return Results.createOk("补差价失败");
        }
    }
}