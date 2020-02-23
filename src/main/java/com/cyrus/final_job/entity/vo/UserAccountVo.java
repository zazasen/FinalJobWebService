package com.cyrus.final_job.entity.vo;

import com.cyrus.final_job.entity.system.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserAccountVo {
    private Integer id;
    private String username;
    private String realName;
    private Boolean enabled;
    private String enabledStr;
    private Long workId;
    private List<Role> roles;
}
