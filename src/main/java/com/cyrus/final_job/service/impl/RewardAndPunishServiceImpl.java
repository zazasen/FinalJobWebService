package com.cyrus.final_job.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.DepartmentDao;
import com.cyrus.final_job.dao.PositionDao;
import com.cyrus.final_job.dao.RewardAndPunishDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.RewardAndPunish;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.RewardAndPunishService;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 员工账套表(RewardAndPunish)表服务实现类
 *
 * @author cyrus
 * @since 2020-03-09 19:41:42
 */
@Service("rewardAndPunishService")
public class RewardAndPunishServiceImpl implements RewardAndPunishService {
    @Resource
    private RewardAndPunishDao rewardAndPunishDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private PositionDao positionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public RewardAndPunish queryById(Integer id) {
        return this.rewardAndPunishDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<RewardAndPunish> queryAllByLimit(int offset, int limit) {
        return this.rewardAndPunishDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param rewardAndPunish 实例对象
     * @return 实例对象
     */
    @Override
    public RewardAndPunish insert(RewardAndPunish rewardAndPunish) {
        this.rewardAndPunishDao.insert(rewardAndPunish);
        return rewardAndPunish;
    }

    /**
     * 修改数据
     *
     * @param rewardAndPunish 实例对象
     * @return 实例对象
     */
    @Override
    public RewardAndPunish update(RewardAndPunish rewardAndPunish) {
        this.rewardAndPunishDao.update(rewardAndPunish);
        return this.queryById(rewardAndPunish.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.rewardAndPunishDao.deleteById(id) > 0;
    }


    @Override
    public Result queryRewardAndPunish(JSONObject params) {
        Integer userId = params.getInteger("userId");
        if (userId == null) {
            return Results.error("userId 不能为空");
        }
        RewardAndPunish rewardAndPunish = new RewardAndPunish();
        rewardAndPunish.setUserId(userId);
        List<RewardAndPunish> list = rewardAndPunishDao.queryAll(rewardAndPunish);
        return Results.createOk(list);
    }

    @Override
    public Result addRewardAndPunish(JSONObject params) {
        RewardAndPunish rewardAndPunish = params.toJavaObject(RewardAndPunish.class);
        Result result = rewardAndPunish.checkParams();
        if (result != null) return result;
        rewardAndPunish.setCreateTime(DateUtils.getNowTime());
        rewardAndPunishDao.insert(rewardAndPunish);
        return Results.createOk("添加成功");
    }
}