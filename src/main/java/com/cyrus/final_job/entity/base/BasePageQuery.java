package com.cyrus.final_job.entity.base;

import lombok.Data;

@Data
public class BasePageQuery {
    private Integer pageSize = 0;
    private Integer pageIndex = 0;
    private Integer offset;

    public void buildLimit() {
        if (null == pageIndex || pageIndex <= 0) {
            pageIndex = 1;
        }
        if (this.pageSize == null || this.pageSize <= 0) {
            pageSize = 5;
        }
        this.offset = (pageIndex - 1) * pageSize;
    }
}
