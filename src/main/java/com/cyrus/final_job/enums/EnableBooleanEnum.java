package com.cyrus.final_job.enums;

import java.util.Objects;

public enum EnableBooleanEnum {

    DISABLE(false, "禁用"),
    ENABLED(true, "启用");

    Boolean code;
    String desc;

    EnableBooleanEnum(Boolean code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Boolean getCode() {
        return code;
    }

    public void setCode(Boolean code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static EnableBooleanEnum getEnumByDesc(String desc) {
        for (EnableBooleanEnum value : EnableBooleanEnum.values()) {
            if (Objects.equals(value.getDesc(), desc)) {
                return value;
            }
        }
        return null;
    }
}
