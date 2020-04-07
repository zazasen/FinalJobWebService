package com.cyrus.final_job.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 考勤统计 vo
 */
@Data
public class CheckInStatisticsDateVo {

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Timestamp date;

    private List<String> data;
}
