package com.cyrus.final_job.entity.condition;

import lombok.Data;

import java.util.Date;

@Data
public class ChatMessageCondition {
    private String from;
    private String to;
    private String content;
    private Date date;
    private String fromRealName;
}
