package com.cyrus.final_job.service.impl;

import com.cyrus.final_job.dao.OvertimeDao;
import com.cyrus.final_job.entity.Overtime;
import com.cyrus.final_job.service.OvertimeService;
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
     * @param limit 查询条数
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
}