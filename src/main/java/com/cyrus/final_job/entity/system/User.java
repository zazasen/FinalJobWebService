package com.cyrus.final_job.entity.system;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.enums.EnabledEnum;
import com.cyrus.final_job.utils.CommonUtils;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 用户表(User)实体类
 *
 * @author cyrus
 * @since 2020-02-16 14:53:32
 */
@ToString
public class User implements Serializable, UserDetails {
    private static final long serialVersionUID = 136575939803333143L;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 登录账号
     */
    private String username;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 用户头像
     */
    private String userFace;
    /**
     * 员工姓名
     */
    private String realName;
    /**
     * 性别,1 男 0 女
     */
    private Integer gender;

    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 0 已婚 1 未婚 2 离异
     */
    private Integer wedlock;
    /**
     * 民族
     */
    private Integer nationId;
    /**
     * 籍贯
     */
    private String nativePlace;
    /**
     * 政治面貌
     */
    private Integer politicsId;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 所属部门id
     */
    private Integer departmentId;
    /**
     * 职位ID
     */
    private Integer positionId;
    /**
     * 最高学历,0 其他 1 小学 2 初中 3 高中 4 大专 5 本科 6 硕士 7 博士
     */
    private Integer topDegree;
    /**
     * 所属专业
     */
    private String specialty;
    /**
     * 毕业院校
     */
    private String school;
    /**
     * 在职状态:1 在职 0 离职
     */
    private Integer workState;
    /**
     * 工号
     */
    private Long workId;
    /**
     * 合同期限
     */
    private Integer contractTerm;
    /**
     * 工龄
     */
    private Double workAge;
    /**
     * 出生日期
     */
    private Timestamp birthday;
    /**
     * 创建时间、入职时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;
    /**
     * 转正日期
     */
    private Timestamp conversionTime;
    /**
     * 离职日期
     */
    private Timestamp departureTime;
    /**
     * 合同起始日期
     */
    private Timestamp beginContractTime;
    /**
     * 合同终止日期
     */
    private Timestamp endContractTime;
    /**
     * 1 true，0 false
     */
    private Boolean enabled;


    private List<Role> roles;

    public Result checkBaseParams() {
        if (StringUtils.isEmpty(this.username)) {
            return Results.error("登录账号不能为空");
        }
        if (StringUtils.isEmpty(this.realName)) {
            return Results.error("用户名不能为空");
        }
        if (Objects.isNull(gender)) {
            return Results.error("用户性别不能为空");
        }
        if (StringUtils.isEmpty(this.idCard)) {
            return Results.error("身份证号不能为空");
        }
        if (Objects.isNull(wedlock)) {
            return Results.error("婚姻状况不能为空");
        }
        if (Objects.isNull(nationId)) {
            return Results.error("所属民族不能为空");
        }
        if (StringUtils.isEmpty(nativePlace)) {
            return Results.error("所属籍贯不能为空");
        }
        if (Objects.isNull(politicsId)) {
            return Results.error("政治面貌不能为空");
        }
        if (StringUtils.isEmpty(email)) {
            return Results.error("邮箱不能为空");
        }
        if (StringUtils.isEmpty(phone)) {
            return Results.error("手机号码不能为空");
        }
        if (StringUtils.isEmpty(address)) {
            return Results.error("联系地址不能为空");
        }
        if (Objects.isNull(departmentId)) {
            return Results.error("所属部门不能为空");
        }
        if (Objects.isNull(positionId)) {
            return Results.error("所属职位不能为空");
        }
        if (Objects.isNull(topDegree)) {
            return Results.error("学历不能为空");
        }
        if (StringUtils.isEmpty(specialty)) {
            return Results.error("所属专业不能为空");
        }
        if (StringUtils.isEmpty(school)) {
            return Results.error("毕业学校不能为空");
        }
        if (Objects.isNull(workAge)) {
            return Results.error("工龄不能为空");
        }
        if (birthday == null) {
            return Results.error("出生日期不能为空");
        }
        return null;
    }


    public Result checkParams() {
        Result result = this.checkBaseParams();
        if (result != null) return result;
        this.password = CommonUtils.getPassword(username);
        this.userFace = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3684533571,3875739943&fm=26&gp=0.jpg";
        this.createTime = DateUtils.getNowTime();
        this.updateTime = DateUtils.getNowTime();
        this.enabled = true;
        return null;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getWedlock() {
        return wedlock;
    }

    public void setWedlock(Integer wedlock) {
        this.wedlock = wedlock;
    }

    public Integer getNationId() {
        return nationId;
    }

    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Integer getPoliticsId() {
        return politicsId;
    }

    public void setPoliticsId(Integer politicsId) {
        this.politicsId = politicsId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getTopDegree() {
        return topDegree;
    }

    public void setTopDegree(Integer topDegree) {
        this.topDegree = topDegree;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getWorkState() {
        return workState;
    }

    public void setWorkState(Integer workState) {
        this.workState = workState;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public Integer getContractTerm() {
        return contractTerm;
    }

    public void setContractTerm(Integer contractTerm) {
        this.contractTerm = contractTerm;
    }

    public Double getWorkAge() {
        return workAge;
    }

    public void setWorkAge(Double workAge) {
        this.workAge = workAge;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getConversionTime() {
        return conversionTime;
    }

    public void setConversionTime(Timestamp conversionTime) {
        this.conversionTime = conversionTime;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public Timestamp getBeginContractTime() {
        return beginContractTime;
    }

    public void setBeginContractTime(Timestamp beginContractTime) {
        this.beginContractTime = beginContractTime;
    }

    public Timestamp getEndContractTime() {
        return endContractTime;
    }

    public void setEndContractTime(Timestamp endContractTime) {
        this.endContractTime = endContractTime;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}