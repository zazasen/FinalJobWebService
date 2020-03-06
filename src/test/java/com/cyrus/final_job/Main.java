package com.cyrus.final_job;

import com.iceyyy.workday.WorkUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class Main {

    public static void main(String[] args) throws Exception{

        LocalDate now = LocalDate.now();
        now = now.plusMonths(1);
        LocalDate first = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate laster = now.with(TemporalAdjusters.lastDayOfMonth());
        int value = laster.getDayOfMonth();
        System.out.println(first);
        System.out.println(value);
        for (int i = 0; i < value; i++) {
            LocalDate localDate = first.plusDays(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String format = formatter.format(localDate);
            boolean workendDay = WorkUtils.isWorkendDay(format);
            System.out.println(localDate.toString() +":"+ workendDay);
        }
    }
}
