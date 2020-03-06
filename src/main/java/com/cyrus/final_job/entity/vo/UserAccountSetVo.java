package com.cyrus.final_job.entity.vo;

import com.cyrus.final_job.entity.AccountSet;
import lombok.Data;

@Data
public class UserAccountSetVo {

    private Integer userId;

    private String realName;
    private Long workId;
    private String phone;
    private String departmentName;
    private String positionName;
    private String positionLevelName;
    private String userAccountSet;

    private Integer accountSetId;

    private AccountSet accountSet;
}
