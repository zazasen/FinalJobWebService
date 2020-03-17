package com.cyrus.final_job.enums;

import java.util.Objects;

public enum RecruitReasonEnum {

    UNKNOWN(-1, "未知"),
    PROFESSIONAL_TITLE_SUPPLEMENT(1, "职称补充"),
    EXPANDED_COMPILATION(2, "扩大编制"),
    RECRUITS_NUMBER(3, "招聘人数"),
    SHORT_TERM_DEMAND(4, "短期需求"),
    RESERVE_TALENTS(5, "储备人才");

    private Integer code;
    private String desc;

    RecruitReasonEnum(Integer code, String desc) {
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

    public static RecruitReasonEnum getEnumByCode(Integer code) {
        for (RecruitReasonEnum value : RecruitReasonEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
