package com.cyrus.final_job.entity.base;


import lombok.Data;

import java.util.List;

@Data
public class ResultPage {
    private Integer code;
    private String msg;
    private Long total;
    private List<?> data;

    public ResultPage() {

    }

    public ResultPage(Integer code, String msg, Long total, List<?> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.total = total;
    }

    public ResultPage(Integer code, Long total, List<?> data) {
        this.code = code;
        this.msg = null;
        this.data = data;
        this.total = total;
    }
}
