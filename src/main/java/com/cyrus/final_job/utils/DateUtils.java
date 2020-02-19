package com.cyrus.final_job.utils;

import java.sql.Timestamp;
import java.util.Date;

public class DateUtils {

    public static Timestamp getNowTime() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

}
