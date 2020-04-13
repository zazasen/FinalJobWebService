package com.cyrus.final_job.enums;

import java.util.Objects;

public enum ReserveStaffStatusEnum {

    UNKNOWN(-1, "未知"),
    READY(0, "尚未接受 offer"),
    ALREADY(1, "接受 offer 待入职"),
    REFUSE(2, "拒绝 offer");

    private Integer code;
    private String desc;

    ReserveStaffStatusEnum(Integer code, String desc) {
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

    public static ReserveStaffStatusEnum getEnumByCode(Integer code) {
        for (ReserveStaffStatusEnum value : ReserveStaffStatusEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
