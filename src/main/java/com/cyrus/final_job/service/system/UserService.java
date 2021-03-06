package com.cyrus.final_job.service.system;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.condition.UserAccountCondition;
import com.cyrus.final_job.entity.system.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户表(User)表服务接口
 *
 * @author cyrus
 * @since 2020-02-16 14:53:32
 */
public interface UserService extends UserDetailsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Integer id);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 员工查询
     *
     * @param params params
     * @return 分页数据
     */
    ResultPage getStaff(JSONObject params);


    /**
     * 添加用户
     *
     * @param params params
     * @return res
     */
    Result addStaff(JSONObject params);

    Result updateStaff(JSONObject params);

    Result delStaff(JSONObject params);

    Result delStaffs(JSONObject params);

    ResponseEntity<byte[]> exportStaff();

    Result importStaff(MultipartFile file);

    /**
     * 更新用户账号信息（更改密码，更改用户拥有的角色，更改账号状态）
     *
     * @param condition
     * @return
     */
    Result updateUserAccount(UserAccountCondition condition);

    ResultPage getUsers(JSONObject params);

    Result getAllUsersExceptOne();

    /**
     * 获取个人信息
     *
     * @param params
     * @return
     */
    Result getMyInfo(JSONObject params);

    /**
     * 更新密码
     *
     * @param params
     * @return
     */
    Result updatePassword(JSONObject params);

    /**
     * 更新用户头像
     *
     * @param file
     * @param id
     * @return
     */
    Result updateUserFace(MultipartFile file, Integer id);

    /**
     * 统计数据
     *
     * @param params
     * @return
     */
    Result getStatisticsDate(JSONObject params);
}