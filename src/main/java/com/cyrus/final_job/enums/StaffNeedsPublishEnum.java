package com.cyrus.final_job.enums;

import java.util.Objects;

public enum StaffNeedsPublishEnum {

    UNKNOWN(-1, "未知"),
    NO_PUBLISH(0, "未发布"),
    PUBLISHED(1, "已发布"),
    DONE(2, "招聘完成");
    private Integer code;
    private String desc;

    StaffNeedsPublishEnum(Integer code, String desc) {
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

    public static StaffNeedsPublishEnum getEnumByCode(Integer code) {
        for (StaffNeedsPublishEnum value : StaffNeedsPublishEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
