package com.cyrus.final_job.entity;

import com.cyrus.final_job.entity.base.BasePageQuery;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.enums.EnabledEnum;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

/**
 * 职位表(Position)实体类
 *
 * @author cyrus
 * @since 2020-02-19 21:03:20
 */
@Data
public class Position extends BasePageQuery {

    private Integer id;
    /**
     * 职位
     */
    private String positionName;
    /**
     * 职位级别 0 初级 1 中级 2 高级
     */
    private Integer positionLevel;
    /**
     * 创建时间
     */
    private Timestamp createTime;

    private Integer enabled;

    private String remark;

    public Result checkParams() {
        if (StringUtils.isEmpty(this.positionName)) {
            return Results.error("职位名不能为空");
        }
        if (Objects.isNull(positionLevel)) {
            return Results.error("职位等级不能为空");
        }
        this.createTime = DateUtils.getNowTime();
        this.enabled = EnabledEnum.ENABLED.getCode();
        return null;
    }

}