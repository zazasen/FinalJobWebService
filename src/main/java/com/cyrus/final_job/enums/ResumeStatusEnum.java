package com.cyrus.final_job.enums;

import com.cyrus.final_job.entity.condition.CommonSelectVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public enum ResumeStatusEnum {

    UNKNOWN(-1, "未知"),
    SCREENING(0, "筛选中"),
    FIRST_INTERVIEW(1, "待一面"),
    SECOND_INTERVIEW(2, "待二面"),
    HR_INTERVIEW(3, "待HR面"),
    PASSED(4, "已通过"),
    TALENT_POOL(5, "人才库"),
    NOT_PASS(6, "未通过");

    private Integer code;
    private String desc;

    ResumeStatusEnum(Integer code, String desc) {
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

    public static ResumeStatusEnum getEnumByCode(Integer code) {
        for (ResumeStatusEnum value : ResumeStatusEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public static List<CommonSelectVo> getResumeStatusList() {
        List<CommonSelectVo> list = new ArrayList<>();
        for (ResumeStatusEnum value : ResumeStatusEnum.values()) {
            if (value.getCode() >= 0) {
                CommonSelectVo vo = new CommonSelectVo();
                vo.setCode(value.getCode());
                vo.setDesc(value.getDesc());
                list.add(vo);
            }
        }
        return list;
    }

    public static List<CommonSelectVo> getStepList() {
        List<CommonSelectVo> list = new ArrayList<>();
        for (ResumeStatusEnum value : ResumeStatusEnum.values()) {
            if (value.getCode() >= 0 && value.getCode() <= 3) {
                CommonSelectVo vo = new CommonSelectVo();
                vo.setCode(value.getCode());
                vo.setDesc(value.getDesc());
                list.add(vo);
            }
        }
        return list;
    }
}
