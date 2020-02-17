package com.cyrus.final_job.entity.system;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 角色表(Role)实体类
 *
 * @author cyrus
 * @since 2020-02-16 14:53:32
 */
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 487262019744191868L;

    private Integer id;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色中文名称
     */
    private String roleZh;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;

}