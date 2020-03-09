package com.cyrus.final_job.service.impl;

import com.cyrus.final_job.dao.QuitJobDao;
import com.cyrus.final_job.entity.QuitJob;
import com.cyrus.final_job.service.QuitJobService;
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
     * @param limit 查询条数
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
}