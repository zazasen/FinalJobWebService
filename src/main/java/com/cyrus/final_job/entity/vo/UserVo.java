package com.cyrus.final_job.entity.vo;

import lombok.Data;

@Data
public class UserVo {
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 登录账号
     */
    private String username;
    /**
     * 用户头像
     */
    private String userFace;
    /**
     * 员工姓名
     */
    private String realName;


    private Boolean enabled;
}
