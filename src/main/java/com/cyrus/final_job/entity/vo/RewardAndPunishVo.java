package com.cyrus.final_job.entity.vo;

import com.cyrus.final_job.entity.RewardAndPunish;
import lombok.Data;

@Data
public class RewardAndPunishVo {

    private Integer userId;

    private String realName;
    private String workId;
    private String departmentName;

    private String positionName;
    private String positionLevelName;

    private RewardAndPunish rewardAndPunish;
}
