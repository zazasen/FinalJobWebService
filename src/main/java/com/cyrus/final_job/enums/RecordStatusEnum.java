package com.cyrus.final_job.enums;

import java.util.Objects;

public enum RecordStatusEnum {

    UNKNOWN(-1, "未知"),
    READY_PASS(0, "待审批"),
    PASSED(1, "已审批");
    private Integer code;
    private String desc;

    RecordStatusEnum(Integer code, String desc) {
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

    public static RecordStatusEnum getEnumByCode(Integer code) {
        for (RecordStatusEnum value : RecordStatusEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
