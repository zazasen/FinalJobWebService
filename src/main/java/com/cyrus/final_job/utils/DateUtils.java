package com.cyrus.final_job.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {


    /**
     * 获取当天上班时间
     *
     * @return
     */
    public static Timestamp getBeginWorkTime() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 10, 00);
        Date date = Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
        return new Timestamp(date.getTime());
    }

    /**
     * 获取当天下班时间
     *
     * @return
     */
    public static Timestamp getEndWorkTime() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 19, 00);
        Date date = Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
        return new Timestamp(date.getTime());
    }

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

    public static Integer getGapDays(LocalDate start, LocalDate end) {
        Period between = Period.between(start, end);
        return between.getDays() + 1;
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

    public static LocalDate getCurrentMonthFirstDay() {
        LocalDate now = LocalDate.now();
        LocalDate first = now.with(TemporalAdjusters.firstDayOfMonth());
        return first;
    }

    public static LocalDate getCurrentMonthLasterDay() {
        LocalDate now = LocalDate.now();
        LocalDate laster = now.with(TemporalAdjusters.lastDayOfMonth());
        return laster;
    }
}
