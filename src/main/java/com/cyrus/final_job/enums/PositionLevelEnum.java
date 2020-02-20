package com.cyrus.final_job.enums;

import com.cyrus.final_job.entity.vo.PositionLevelVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public enum PositionLevelEnum {

    UNKNOWN(-1, "未知"),
    LOW(0, "初级"),
    MIDDLE(1, "中级"),
    HIGH(2, "高级");

    Integer code;
    String desc;

    private static List<PositionLevelVo> list = new ArrayList<>();

    PositionLevelEnum(Integer code, String desc) {
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

    public static PositionLevelEnum getEnumByCode(Integer code) {
        for (PositionLevelEnum value : PositionLevelEnum.values()) {
            if (Objects.equals(code, value.getCode())) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public static List<PositionLevelVo> getPositionLevelList() {
        if (list.size() > 0) {
            list.clear();
        }
        for (PositionLevelEnum value : PositionLevelEnum.values()) {
            if (value.code < 0) continue;
            PositionLevelVo positionLevelVo = new PositionLevelVo(value.getCode(), value.getDesc());
            list.add(positionLevelVo);
        }
        return list;
    }
}
