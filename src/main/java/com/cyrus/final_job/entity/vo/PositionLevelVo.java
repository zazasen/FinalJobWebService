package com.cyrus.final_job.entity.vo;

import lombok.Data;

@Data
public class PositionLevelVo {
    private Integer code;
    private String level;

    public PositionLevelVo() {
    }

    public PositionLevelVo(Integer code, String level) {
        this.code = code;
        this.level = level;
    }
}
