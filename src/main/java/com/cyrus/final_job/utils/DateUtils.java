package com.cyrus.final_job.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Timestamp getNowTime() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    public static int getNowHour() {
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

    /**
     * 获取当年第一天
     *
     * @return
     */
    public static Timestamp getCurrYearFirst() {
        int year = LocalDate.now().getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return new Timestamp(currYearFirst.getTime());
    }

    /**
     * 获取当年最后一天
     *
     * @return
     */
    public static Timestamp getCurrYearLaster() {
        int year = LocalDate.now().getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearFirst = calendar.getTime();
        Timestamp timestamp = new Timestamp(currYearFirst.getTime());
        return timestamp;
    }
}
