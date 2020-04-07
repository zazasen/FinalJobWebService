package com.cyrus.final_job.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 统计 vo
 */
@Data
public class StatisticsDateVo {

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Timestamp date;

    @JsonFormat(pattern = "MM", timezone = "GMT+8")
    private Timestamp statisticsUsersDate;

    private List<String> data;
}
