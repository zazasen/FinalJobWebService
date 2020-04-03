package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.AccountSetDao;
import com.cyrus.final_job.dao.ContractDao;
import com.cyrus.final_job.dao.DepartmentDao;
import com.cyrus.final_job.dao.UserAccountSetDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.AccountSet;
import com.cyrus.final_job.entity.Contract;
import com.cyrus.final_job.entity.Department;
import com.cyrus.final_job.entity.UserAccountSet;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.condition.ContractCondition;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.entity.vo.ContractVo;
import com.cyrus.final_job.enums.ConfirmStateEnum;
import com.cyrus.final_job.enums.WorkStateEnum;
import com.cyrus.final_job.service.ContractService;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * 员工合同表(Contract)表服务实现类
 *
 * @author cyrus
 * @since 2020-03-12 15:22:35
 */
@Service("contractService")
public class ContractServiceImpl implements ContractService {
    @Resource
    private ContractDao contractDao;

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private AccountSetDao accountSetDao;

    @Autowired
    private UserAccountSetDao userAccountSetDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Contract queryById(Integer id) {
        return this.contractDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Contract> queryAllByLimit(int offset, int limit) {
        return this.contractDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param contract 实例对象
     * @return 实例对象
     */
    @Override
    public Contract insert(Contract contract) {
        this.contractDao.insert(contract);
        return contract;
    }

    /**
     * 修改数据
     *
     * @param contract 实例对象
     * @return 实例对象
     */
    @Override
    public Contract update(Contract contract) {
        this.contractDao.update(contract);
        return this.queryById(contract.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.contractDao.deleteById(id) > 0;
    }

    @Override
    public String getContract(Integer userId) {
        Contract contract = contractDao.queryByUserId(userId);
        String process = null;
        if (StringUtils.isEmpty(contract.getContent())) {
            process = getContractMethod(userId, false);
        } else if (ConfirmStateEnum.READY_SIGNED.equals(ConfirmStateEnum.getEnumByCode(contract.getConfirm()))) {
            process = getContractMethod(userId, false);
        } else {
            process = contract.getContent();
        }
        return process;
    }

    /**
     * @param userId
     * @param confirm 员工是否确认合同
     * @return
     */
    private String getContractMethod(Integer userId, Boolean confirm) {
        User user = userDao.queryById(userId);
        Context context = new Context();
        context.setVariable("realName", user.getRealName());
        context.setVariable("address", user.getAddress());
        context.setVariable("idCard", user.getIdCard());
        context.setVariable("phone", user.getPhone());
        context.setVariable("beginContractTime", LocalDate.now().toString());
        context.setVariable("endContractTime", LocalDate.now().plusYears(2).toString());
        UserAccountSet userAccountSet = userAccountSetDao.queryByUserId(userId);
        if (userAccountSet != null) {
            AccountSet accountSet = accountSetDao.queryById(userAccountSet.getAccountSetId());
            if (accountSet != null) {
                context.setVariable("basicSalary", accountSet.getBasicSalary());
            } else {
                context.setVariable("basicSalary", "---");
            }
        } else {
            context.setVariable("basicSalary", "---");
        }
        if (confirm) {
            context.setVariable("confirm", user.getRealName());
        }
        String process = templateEngine.process("Contract.html", context);
        return process;
    }


    @Override
    public ResultPage getAllContract(JSONObject params) {
        ContractCondition condition = params.toJavaObject(ContractCondition.class);
        condition.buildLimit();

        List<Contract> contracts = contractDao.queryAllByCondition(condition);
        List<ContractVo> vos = JSONArray.parseArray(JSONArray.toJSONString(contracts), ContractVo.class);
        for (ContractVo vo : vos) {
            User user = userDao.queryById(vo.getUserId());
            vo.setRealName(user.getRealName());
            vo.setUserId(user.getId());
            vo.setWorkID(user.getWorkId());
            vo.setPhone(user.getPhone());
            vo.setEmail(user.getEmail());
            Department department = departmentDao.queryById(user.getDepartmentId());
            vo.setDepartmentName(department.getName());
            vo.setBeginContractTime(vo.getBeginTime());
            vo.setEndContractTime(vo.getEndTime());
            vo.setConfirmStr(ConfirmStateEnum.getEnumByCode(vo.getConfirm()).getDesc());
        }
        Long total = contractDao.queryAllByConditionCount(condition);
        return Results.createOk(total, vos);
    }

    @Override
    public Result addContract(JSONObject params) {
        Integer userId = params.getInteger("userId");
        Contract contract = contractDao.queryByUserId(userId);
        if (ConfirmStateEnum.SIGNED.equals(ConfirmStateEnum.getEnumByCode(contract.getConfirm()))) {
            return Results.error("发起失败，该员工已经签约");
        }
        String process = getContract(userId);
        contract.setContent(process);
        contract.setConfirm(ConfirmStateEnum.READY_SIGNED.getCode());
        contract.setBeginTime(new Timestamp(LocalDate.now().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli()));
        contract.setEndTime(new Timestamp(LocalDate.now().plusYears(2).atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli()));
        contractDao.update(contract);
        return Results.createOk("发起成功");
    }

    @Override
    public Result getMyContract() {
        int userId = UserUtils.getCurrentUserId();
        Contract contract = contractDao.queryByUserId(userId);
        if (contract == null) {
            return Results.createOk(null);
        } else {
            List<ContractVo> list = new ArrayList<>();
            ContractVo vo = new ContractVo();
            vo.setBeginContractTime(contract.getBeginTime());
            vo.setEndContractTime(contract.getEndTime());
            vo.setConfirmStr(ConfirmStateEnum.getEnumByCode(contract.getConfirm()).getDesc());
            list.add(vo);
            return Results.createOk(list);
        }
    }

    @Override
    public Result confirmAdd() {
        int userId = UserUtils.getCurrentUserId();
        Contract contract = contractDao.queryByUserId(userId);
        if (contract == null) {
            return Results.error("确认合同失败");
        }
        if (ConfirmStateEnum.SIGNED.equals(ConfirmStateEnum.getEnumByCode(contract.getConfirm()))) {
            return Results.error("您已经签过合同了，不必再签");
        }
        String process = getContractMethod(userId, true);
        contract.setContent(process);
        contract.setConfirm(ConfirmStateEnum.SIGNED.getCode());
        contractDao.update(contract);

        User user = userDao.queryById(userId);
        user.setWorkState(WorkStateEnum.IN.getCode());
        // 合同期限
        user.setContractTerm(2);
        user.setBeginContractTime(new Timestamp(LocalDate.now().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli()));
        user.setEndContractTime(new Timestamp(LocalDate.now().plusYears(2).atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli()));
        userDao.update(user);
        return Results.createOk("确认成功");
    }
}