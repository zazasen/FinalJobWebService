package com.cyrus.final_job.enums;

import java.util.Objects;

public enum HolidayTypeEnum {
    UNKNOWN(-1, "未知"),
    EXCHANGE(0, "调休"),
    SICK_LEAVE(1, "病假"),
    ANNUAL_LEAVE(2, "年假"),
    BREASTFEEDING_LEAVE(3, "哺乳假"),
    MARRIAGE_HOLIDAY(4, "婚假"),
    FUNERAL_LEAVE(5, "丧假");
    private Integer code;
    private String desc;

    HolidayTypeEnum(Integer code, String desc) {
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


    public static HolidayTypeEnum getEnumByCode(Integer code) {
        for (HolidayTypeEnum value : HolidayTypeEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
