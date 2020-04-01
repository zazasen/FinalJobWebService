package com.cyrus.final_job.service.impl;

import com.cyrus.final_job.dao.ResumeDao;
import com.cyrus.final_job.entity.Resume;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.condition.InputResumeCondition;
import com.cyrus.final_job.service.ResumeService;
import com.cyrus.final_job.utils.FastDFSUtils;
import com.cyrus.final_job.utils.Results;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 简历表(Resume)表服务实现类
 *
 * @author cyrus
 * @since 2020-04-01 14:51:57
 */
@Service("resumeService")
public class ResumeServiceImpl implements ResumeService {
    @Resource
    private ResumeDao resumeDao;

    @Value("${fastdfs.nginx.host}")
    private String nginxHost;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Resume queryById(Integer id) {
        return this.resumeDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Resume> queryAllByLimit(int offset, int limit) {
        return this.resumeDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param resume 实例对象
     * @return 实例对象
     */
    @Override
    public Resume insert(Resume resume) {
        this.resumeDao.insert(resume);
        return resume;
    }

    /**
     * 修改数据
     *
     * @param resume 实例对象
     * @return 实例对象
     */
    @Override
    public Resume update(Resume resume) {
        this.resumeDao.update(resume);
        return this.queryById(resume.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.resumeDao.deleteById(id) > 0;
    }

    @Override
    public Result inputResume(InputResumeCondition condition) {
        Result result = condition.checkParams();
        if (result != null) return result;
        Resume resume = condition.buildResume();
        String fileId = FastDFSUtils.upload(condition.getFile());
        String url = nginxHost + fileId;
        resume.setUrl(url);
        resumeDao.insert(resume);
        return Results.createOk("投递成功");
    }
}