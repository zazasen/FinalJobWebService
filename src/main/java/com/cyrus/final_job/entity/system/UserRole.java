package com.cyrus.final_job.entity.system;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色关联表(UserRole)实体类
 *
 * @author cyrus
 * @since 2020-02-16 14:53:33
 */
@Data
public class UserRole implements Serializable {
    private static final long serialVersionUID = 329139114986913845L;

    private Integer id;
    /**
     * user表id
     */
    private Integer userId;
    /**
     * role表id
     */
    private Integer roleId;

}