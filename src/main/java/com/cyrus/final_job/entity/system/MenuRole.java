package com.cyrus.final_job.entity.system;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜单角色表(MenuRole)实体类
 *
 * @author cyrus
 * @since 2020-02-16 14:53:32
 */
@Data
public class MenuRole implements Serializable {
    private static final long serialVersionUID = 393253219656557516L;

    private Integer id;
    /**
     * menu表id
     */
    private Integer menuId;
    /**
     * role表id
     */
    private Integer roleId;

}