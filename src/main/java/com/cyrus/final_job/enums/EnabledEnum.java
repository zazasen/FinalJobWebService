package com.cyrus.final_job.enums;

import lombok.Data;


public enum EnabledEnum {
    UNENABLED(0, "禁用"),
    ENABLED(1, "启用");

    Integer code;
    String desc;

    EnabledEnum(Integer code, String desc) {
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
}
