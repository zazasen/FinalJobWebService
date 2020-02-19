package com.cyrus.final_job.enums;


import java.util.Objects;

public enum IsParentEnum {
    UNKNOWN(-1,"未知"),
    NOTPARENT(0, "不是父节点"),
    ISPARENT(1, "是父节点");

    Integer code;
    String desc;

    IsParentEnum(Integer code, String desc) {
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

    public static IsParentEnum getEnumByCode(Integer code) {
        for (IsParentEnum value : IsParentEnum.values()) {
            if (Objects.equals(code, value.getCode())) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
