package com.cyrus.final_job.enums;

import java.util.Objects;

public enum SignInTypeEnum {
    UNKNOWN(-1, "未知"),
    NORMAL(0, "正常签到"),
    OUT_SIGN(1, "外勤签到"),
    REMEDY_SIGN(2, "补签"),
    WITHOUT_SIGN(3, "未签到"),
    OVERTIME_SIGN(4, "周末加班签到");

    private Integer code;
    private String desc;

    SignInTypeEnum(Integer code, String desc) {
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

    public static SignInTypeEnum getEnumByCode(Integer code) {
        for (SignInTypeEnum value : SignInTypeEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
