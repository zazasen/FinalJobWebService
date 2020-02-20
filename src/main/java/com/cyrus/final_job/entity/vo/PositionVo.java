package com.cyrus.final_job.entity.vo;

import com.cyrus.final_job.entity.Position;
import lombok.Data;

@Data
public class PositionVo extends Position {
    /**
     * 职位等级字符串
     */
    private String positionLevelStr;

    private String enabledStr;
}
