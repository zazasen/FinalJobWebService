package com.cyrus.final_job.service.impl;

import com.cyrus.final_job.dao.PoliticsStatusDao;
import com.cyrus.final_job.entity.PoliticsStatus;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.PoliticsStatusService;
import com.cyrus.final_job.utils.Results;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 政治面貌表(PoliticsStatus)表服务实现类
 *
 * @author cyrus
 * @since 2020-02-20 16:00:32
 */
@Service("politicsStatusService")
public class PoliticsStatusServiceImpl implements PoliticsStatusService {
    @Resource
    private PoliticsStatusDao politicsStatusDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PoliticsStatus queryById(Integer id) {
        return this.politicsStatusDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<PoliticsStatus> queryAllByLimit(int offset, int limit) {
        return this.politicsStatusDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param politicsStatus 实例对象
     * @return 实例对象
     */
    @Override
    public PoliticsStatus insert(PoliticsStatus politicsStatus) {
        this.politicsStatusDao.insert(politicsStatus);
        return politicsStatus;
    }

    /**
     * 修改数据
     *
     * @param politicsStatus 实例对象
     * @return 实例对象
     */
    @Override
    public PoliticsStatus update(PoliticsStatus politicsStatus) {
        this.politicsStatusDao.update(politicsStatus);
        return this.queryById(politicsStatus.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.politicsStatusDao.deleteById(id) > 0;
    }

    @Override
    public Result getPoliticsStatus() {
        List<PoliticsStatus> politicsStatuses = politicsStatusDao.queryAll(new PoliticsStatus());
        return Results.createOk(politicsStatuses);
    }
}