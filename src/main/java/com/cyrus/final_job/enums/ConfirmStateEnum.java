package com.cyrus.final_job.enums;

import java.util.Objects;

public enum ConfirmStateEnum {

    UNKNOWN(-1, "未知"),
    NO_SIGN(0, "未签署"),
    READY_SIGNED(1, "待签署"),
    SIGNED(2, "已签署");


    private Integer code;
    private String desc;

    ConfirmStateEnum(Integer code, String desc) {
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

    public static ConfirmStateEnum getEnumByCode(Integer code) {
        for (ConfirmStateEnum value : ConfirmStateEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return UNKNOWN;
    }

}
