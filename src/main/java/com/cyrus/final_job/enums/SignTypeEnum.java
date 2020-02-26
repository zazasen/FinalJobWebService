package com.cyrus.final_job.enums;

import java.util.Objects;

public enum SignTypeEnum {
    UNKNOWN(-1, "未知"),
    HALF(0, "半天班"),
    FULL(1, "一天班"),
    FREE(2, "请假");

    private Integer code;
    private String desc;

    SignTypeEnum(Integer code, String desc) {
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

    public static SignTypeEnum getEnumByCode(Integer code) {
        for (SignTypeEnum value : SignTypeEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
