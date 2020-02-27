package com.cyrus.final_job.enums;

import java.util.Objects;

public enum ApprovalTypeEnum {
    UNKNOWN(-1, "未知"),
    START_REMEDY_SIGN(0, "签到补卡"),
    END_REMEDY_SIGN(1, "签退补卡"),
    LEAVE(2, "请假"),
    OVERTIME(3, "加班");

    private Integer code;
    private String desc;

    ApprovalTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static ApprovalTypeEnum getEnumByCode(Integer code) {
        for (ApprovalTypeEnum value : ApprovalTypeEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
