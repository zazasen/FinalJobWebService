package com.cyrus.final_job.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class DateUtils {

    public static Timestamp getNowTime() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    public static int getNowHour(){
        LocalDateTime now = LocalDateTime.now();
        return now.getHour();
    }

    /**
     * 获取两个时间间隔的小时数
     *
     * @param start
     * @param end
     * @return
     */
    public static double getGapTime(LocalDateTime start, LocalDateTime end) {
        Duration between = Duration.between(start, end);
        long minutes = between.toMinutes();
        double res = minutes / 60.0 + (minutes % 60) / 60;
        DecimalFormat df = new DecimalFormat("#.0");
        String str = df.format(res);
        return Double.parseDouble(str);
    }
}
