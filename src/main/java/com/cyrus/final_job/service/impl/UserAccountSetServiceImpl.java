package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.AccountSetDao;
import com.cyrus.final_job.dao.DepartmentDao;
import com.cyrus.final_job.dao.PositionDao;
import com.cyrus.final_job.dao.UserAccountSetDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.AccountSet;
import com.cyrus.final_job.entity.Position;
import com.cyrus.final_job.entity.UserAccountSet;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.condition.UserAccountSetQueryCondition;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.entity.vo.UserAccountSetVo;
import com.cyrus.final_job.enums.EnableBooleanEnum;
import com.cyrus.final_job.enums.PositionLevelEnum;
import com.cyrus.final_job.service.UserAccountSetService;
import com.cyrus.final_job.utils.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 员工账套表(UserAccountSet)表服务实现类
 *
 * @author cyrus
 * @since 2020-03-06 10:01:22
 */
@Service("userAccountSetService")
public class UserAccountSetServiceImpl implements UserAccountSetService {
    @Resource
    private UserAccountSetDao userAccountSetDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private AccountSetDao accountSetDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserAccountSet queryById(Integer id) {
        return this.userAccountSetDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<UserAccountSet> queryAllByLimit(int offset, int limit) {
        return this.userAccountSetDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param userAccountSet 实例对象
     * @return 实例对象
     */
    @Override
    public UserAccountSet insert(UserAccountSet userAccountSet) {
        this.userAccountSetDao.insert(userAccountSet);
        return userAccountSet;
    }

    /**
     * 修改数据
     *
     * @param userAccountSet 实例对象
     * @return 实例对象
     */
    @Override
    public UserAccountSet update(UserAccountSet userAccountSet) {
        this.userAccountSetDao.update(userAccountSet);
        return this.queryById(userAccountSet.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userAccountSetDao.deleteById(id) > 0;
    }

    @Override
    public ResultPage getUserAccountSet(JSONObject params) {
        UserAccountSetQueryCondition condition = params.toJavaObject(UserAccountSetQueryCondition.class);
        condition.buildLimit();
        User user = new User();
        user.setRealName(condition.getRealName());
        user.setWorkId(condition.getWorkId());
        user.setDepartmentId(condition.getDepartmentId());
        user.setEnabled(EnableBooleanEnum.ENABLED.getCode());
        List<User> users = userDao.getUserByUserAccountSetCondition(condition);
        List<UserAccountSetVo> userAccountSetVos = new ArrayList<>();
        for (User item : users) {
            UserAccountSetVo vo = new UserAccountSetVo();
            vo.setUserId(item.getId());
            vo.setRealName(item.getRealName());
            vo.setWorkId(item.getWorkId());
            vo.setPhone(item.getPhone());
            vo.setDepartmentName(departmentDao.queryById(item.getDepartmentId()).getName());
            Position position = positionDao.queryById(item.getPositionId());
            vo.setPositionName(position.getPositionName());
            vo.setPositionLevelName(PositionLevelEnum.getEnumByCode(position.getPositionLevel()).getDesc());
            UserAccountSet userAccountSet = userAccountSetDao.queryByUserId(item.getId());
            if (userAccountSet == null) {
                vo.setUserAccountSet("尚未设置账套");
                vo.setAccountSet(null);
            } else {
                AccountSet accountSet = accountSetDao.queryById(userAccountSet.getAccountSetId());
                vo.setAccountSetId(accountSet.getId());
                vo.setAccountSet(accountSet);
                vo.setUserAccountSet(accountSet.getAccountName());
            }
            userAccountSetVos.add(vo);
        }
        Long total = userDao.getUserByUserAccountSetConditionCount(condition);
        return Results.createOk(total, userAccountSetVos);
    }

    @Override
    public Result updateUserAccountSet(JSONObject params) {
        Integer accountSetId = params.getInteger("accountSetId");
        if (accountSetId == null) return Results.error("账套不能为空");
        Integer userId = params.getInteger("userId");
        if (userId == null) return Results.error("用户 id 为空");
        UserAccountSet userAccountSet = userAccountSetDao.queryByUserId(userId);
        // 改员工刚开始没有设置过账套，为其设置账套
        if (userAccountSet == null) {
            userAccountSet = new UserAccountSet();
            userAccountSet.setUserId(userId);
            userAccountSet.setAccountSetId(accountSetId);
            userAccountSetDao.insert(userAccountSet);
        } else {
            // 该员工之前设置过账套，为其更新账套
            userAccountSet.setUserId(userId);
            userAccountSet.setAccountSetId(accountSetId);
            userAccountSetDao.update(userAccountSet);
        }
        return Results.createOk("设置账套成功");
    }
}