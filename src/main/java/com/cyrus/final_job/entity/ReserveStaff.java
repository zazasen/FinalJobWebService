package com.cyrus.final_job.entity;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.utils.Results;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 预备员工表(ReserveStaff)实体类
 *
 * @author cyrus
 * @since 2020-04-13 10:33:09
 */
@Data
public class ReserveStaff {

    /**
     * 预备员工 id
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 部门id
     */
    private Integer departmentId;
    /**
     * 职位id
     */
    private Integer positionId;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 入职时间
     */
    private Timestamp entryTime;
    /**
     * 员工状态：0 尚未接受 offer，1 接受 offer 待入职
     */
    private Integer status;

    public Result checkParams() {
        if (StringUtils.isEmpty(name)) {
            return Results.error("姓名不能为空");
        }
        if (StringUtils.isEmpty(phone)) {
            return Results.error("手机号码不能为空");
        }
        if (StringUtils.isEmpty(email)) {
            return Results.error("邮箱不能为空");
        }
        if (Objects.isNull(departmentId)) {
            return Results.error("部门不能为空");
        }
        if (Objects.isNull(positionId)) {
            return Results.error("职位不能为空");
        }
        if (Objects.isNull(entryTime)) {
            return Results.error("入职时间不能为空");
        }
        if (Objects.isNull(status)) {
            return Results.error("员工状态不能为空");
        }
        return null;
    }
}