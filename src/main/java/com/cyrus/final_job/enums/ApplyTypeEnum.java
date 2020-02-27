package com.cyrus.final_job.enums;

import java.util.Objects;

public enum ApplyTypeEnum {

    UNKNOWN(-1, "未知"),
    START_SIGN_APPlY(0, "签到补卡"),
    END_SIGN_APPLY(1, "签退补卡");

    private Integer code;
    private String desc;

    ApplyTypeEnum(Integer code, String desc) {
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

    public static ApplyTypeEnum getEnumByCode(Integer code) {
        for (ApplyTypeEnum value : ApplyTypeEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
