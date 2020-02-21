package com.cyrus.final_job.service.impl.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.DepartmentDao;
import com.cyrus.final_job.dao.NationDao;
import com.cyrus.final_job.dao.PoliticsStatusDao;
import com.cyrus.final_job.dao.PositionDao;
import com.cyrus.final_job.dao.system.RoleDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.dao.system.UserRoleDao;
import com.cyrus.final_job.entity.Position;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.condition.UserCondition;
import com.cyrus.final_job.entity.system.Role;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.entity.system.UserRole;
import com.cyrus.final_job.entity.vo.UserDetailVo;
import com.cyrus.final_job.enums.*;
import com.cyrus.final_job.service.system.UserService;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author cyrus
 * @since 2020-02-16 14:53:32
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private NationDao nationDao;

    @Autowired
    private PoliticsStatusDao politicsStatusDao;

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
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    @Override
    public ResultPage getStaff(JSONObject params) {
        UserCondition userCondition = params.toJavaObject(UserCondition.class);
        userCondition.buildLimit();
        if (userCondition.getRealName() == null) userCondition.setRealName("");
        List<UserDetailVo> userDetailVoList = userDao.queryStaffByCondition(userCondition);
        for (UserDetailVo item : userDetailVoList) {
            item.setGenderStr(GenderEnum.getEnumByCode(item.getGender()).getDesc());
            item.setWedlockStr(WedlockEnum.getEnumByCode(item.getWedlock()).getDesc());
            item.setNationName(nationDao.queryById(item.getNationId()).getName());
            item.setPoliticsStr(politicsStatusDao.queryById(item.getPoliticsId()).getName());
            item.setDepartmentName(departmentDao.queryById(item.getDepartmentId()).getName());
            Position position = positionDao.queryById(item.getPositionId());
            item.setPositionName(position.getPositionName());
            item.setTopDegreeStr(DegreeEnum.getEnumByCode(item.getTopDegree()).getDesc());
            item.setWorkStateStr(WorkStateEnum.getEnumByCode(item.getWorkState()).getDesc());
            item.setPositionLevelName(PositionLevelEnum.getEnumByCode(position.getPositionLevel()).getDesc());
            item.setWorkAgeStr(item.getWorkAge() + "年");
            item.setEnabledStr(Boolean.TRUE.equals(item.getEnabled()) ? "启用" : "禁用");
            if (Objects.isNull(item.getContractTerm())) {
                item.setContractTermStr("");
            } else {
                item.setContractTermStr(item.getContractTerm() + "天");
            }
        }
        Long total = userDao.queryStaffCountByCondition(userCondition);
        return Results.createOk(total, userDetailVoList);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        this.userDao.update(user);
        return this.queryById(user.getId());
    }

    @Override
    public Result updateStaff(JSONObject params) {
        User user = params.toJavaObject(User.class);
        Result result = user.checkBaseParams();
        if (result != null) return result;
        user.setUpdateTime(DateUtils.getNowTime());
        userDao.update(user);
        return Results.createOk("更新成功");
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userDao.deleteById(id) > 0;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 根据用户id 从 user_role 表中查找出所有的 role id;
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        List<UserRole> userRoles = userRoleDao.queryAll(userRole);
        List<Integer> roleIds = userRoles.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        // 获取该用户下的所有角色信息
        List<Role> roles = roleDao.getRolesByIds(roleIds);
        user.setRoles(roles);
        return user;
    }

    @Override
    public Result addStaff(JSONObject params) {
        User user = params.toJavaObject(User.class);
        user.checkParams();
        Long maxWorkId = userDao.getMaxWorkId();
        user.setWorkId(maxWorkId + 1);
        userDao.insert(user);
        return Results.createOk("添加成功");
    }

    @Override
    public Result delStaff(JSONObject params) {
        Integer id = params.getInteger("id");
        if (Objects.isNull(id)) {
            return Results.error("id 不能为空");
        }
        int userId = UserUtils.getCurrentUserId();
        if (Objects.equals(id, userId)) {
            return Results.error("您不能删除自己");
        }
        userDao.deleteById(id);
        return Results.createOk("删除成功");
    }


    @Override
    public Result delStaffs(JSONObject params) {
        JSONArray array = params.getJSONArray("ids");
        if (array == null || array.isEmpty()) return Results.error("ids 参数不能为空");
        List<Integer> ids = JSONArray.parseArray(JSONObject.toJSONString(array), Integer.class);
        for (Integer id : ids) {
            if (Objects.equals(id, UserUtils.getCurrentUserId())) {
                return Results.error("您不能删除自己");
            }
        }
        for (Integer id : ids) {
            userDao.deleteById(id);
        }
        return Results.createOk("删除成功");
    }
}