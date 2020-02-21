package com.cyrus.final_job.enums;

import java.util.Objects;

public enum WorkStateEnum {
    UNKNOWN(-1, "未知"),
    IN(1, "在职"),
    OUT(0, "离职");

    private Integer code;
    private String desc;

    WorkStateEnum(Integer code, String desc) {
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

    public static WorkStateEnum getEnumByCode(Integer code) {
        for (WorkStateEnum value : WorkStateEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
