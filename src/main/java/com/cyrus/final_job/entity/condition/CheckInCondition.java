package com.cyrus.final_job.entity.condition;

import com.cyrus.final_job.entity.base.BasePageQuery;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CheckInCondition extends BasePageQuery {
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer userId;
    private String realName;
    private Integer departmentId;

    private String beginTime;
    private String tailTime;

    public void buildTime() {
        if (this.getStartTime() != null && this.getEndTime() != null) {
            this.setBeginTime(this.getStartTime().toLocalDateTime().toLocalDate().toString());
            this.setTailTime(this.getEndTime().toLocalDateTime().toLocalDate().toString());
        }
    }
}
