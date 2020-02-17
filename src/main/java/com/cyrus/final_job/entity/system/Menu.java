package com.cyrus.final_job.entity.system;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜单管理表(Menu)实体类
 *
 * @author cyrus
 * @since 2020-02-16 14:53:28
 */
@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = 559748320442763084L;

    private Integer id;
    /**
     * 资源url,后端的
     */
    private String url;
    /**
     * 前端请求路径,router路径
     */
    private String path;
    /**
     * 前端组件名字
     */
    private String component;
    /**
     * 菜单局域网名称
     */
    private String name;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 组件切换的时候前一个组件是否存活
     */
    private Integer keepAlive;
    /**
     * 组件是否需要登录才能访问
     */
    private Integer requireAuth;
    /**
     * 上级菜单id
     */
    private Integer parentId;
    /**
     * 菜单项是否启用
     */
    private Integer enabled;

}