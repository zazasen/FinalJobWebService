package com.cyrus.final_job.enums;

import java.util.Objects;

public enum GenderEnum {

    UNKNOWN(-1, "未知"),
    WOMAN(0, "女"),
    MAN(1, "男");

    private Integer code;
    private String desc;

    GenderEnum(Integer code, String desc) {
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

    public static GenderEnum getEnumByCode(Integer code) {
        for (GenderEnum value : GenderEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
