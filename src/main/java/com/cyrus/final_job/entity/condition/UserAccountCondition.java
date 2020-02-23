package com.cyrus.final_job.entity.condition;

import lombok.Data;

@Data
public class UserAccountCondition {
    private Integer id;
    private String password;
    private Integer[] roleIds;
    private Boolean enabled;
}
