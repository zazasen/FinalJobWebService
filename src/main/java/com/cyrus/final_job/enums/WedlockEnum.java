package com.cyrus.final_job.enums;

import java.util.Objects;

public enum WedlockEnum {

    UNKNOWN(-1, "未知"),
    MARRIED(0, "已婚"),
    UNMARRIED(1, "未婚"),
    DIVORCED(2, "离异");

    private Integer code;
    private String desc;

    WedlockEnum(Integer code, String desc) {
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

    public static WedlockEnum getEnumByCode(Integer code) {
        for (WedlockEnum value : WedlockEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
