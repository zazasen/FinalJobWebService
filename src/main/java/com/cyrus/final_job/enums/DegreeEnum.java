package com.cyrus.final_job.enums;

import java.util.Objects;

public enum DegreeEnum {
    UNKNOWN(-1,"未知"),
    OTHER(0,"其他"),
    PRIMARY(1,"小学"),
    JUNIOR(2,"初中"),
    HIGH(3,"高中"),
    SPECIALTY(4,"专科"),
    UNDERGRADUATE(5,"本科"),
    MASTER(6,"硕士"),
    DOCTOR(7,"博士");


    private Integer code;
    private String desc;

    DegreeEnum(Integer code, String desc) {
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

    public static DegreeEnum getEnumByCode(Integer code) {
        for (DegreeEnum value : DegreeEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
