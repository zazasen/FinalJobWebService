package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.AccountSetDao;
import com.cyrus.final_job.entity.AccountSet;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.condition.AccountSetCondition;
import com.cyrus.final_job.service.AccountSetService;
import com.cyrus.final_job.utils.Results;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 薪资账套表(AccountSet)表服务实现类
 *
 * @author cyrus
 * @since 2020-03-05 14:10:17
 */
@Service("accountSetService")
public class AccountSetServiceImpl implements AccountSetService {
    @Resource
    private AccountSetDao accountSetDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AccountSet queryById(Integer id) {
        return this.accountSetDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<AccountSet> queryAllByLimit(int offset, int limit) {
        return this.accountSetDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param accountSet 实例对象
     * @return 实例对象
     */
    @Override
    public AccountSet insert(AccountSet accountSet) {
        this.accountSetDao.insert(accountSet);
        return accountSet;
    }

    /**
     * 修改数据
     *
     * @param accountSet 实例对象
     * @return 实例对象
     */
    @Override
    public AccountSet update(AccountSet accountSet) {
        this.accountSetDao.update(accountSet);
        return this.queryById(accountSet.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.accountSetDao.deleteById(id) > 0;
    }

    @Override
    public Result addAccountSet(JSONObject params) {
        AccountSet accountSet = params.toJavaObject(AccountSet.class);
        Result result = accountSet.checkAndBuildParams();
        if (result != null) return result;
        accountSetDao.insert(accountSet);
        return Results.createOk("添加账套成功");
    }

    @Override
    public ResultPage queryAccountSet(JSONObject params) {
        AccountSetCondition condition = params.toJavaObject(AccountSetCondition.class);
        condition.buildLimit();
        List<AccountSet> accountSets = accountSetDao.queryAllByLimit(condition.getOffset(), condition.getPageSize());
        Long total = accountSetDao.queryAllByLimitCount();
        return Results.createOk(total, accountSets);
    }

    @Override
    public Result editAccountSet(JSONObject params) {
        AccountSet accountSet = params.toJavaObject(AccountSet.class);
        if (accountSet.getId() == null) return Results.error("id 不能为空");
        Result result = accountSet.checkAndBuildParams();
        if (result != null) {
            return result;
        }
        accountSetDao.update(accountSet);
        return Results.createOk("更新账套成功");
    }

    @Override
    public Result delAccountSet(JSONObject params) {
        Integer id = params.getInteger("id");
        if (id == null) return Results.error("id 不能为空");
        accountSetDao.deleteById(id);
        return Results.createOk("删除成功");
    }
}