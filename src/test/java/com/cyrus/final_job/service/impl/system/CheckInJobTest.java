package com.cyrus.final_job.service.impl.system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@SpringBootTest
public class CheckInJobTest {

    @Test
    public void checkIn() {
        LocalDate now = LocalDate.now();
        LocalDate first = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate laster = now.with(TemporalAdjusters.lastDayOfMonth());
        int value = laster.getDayOfMonth();
        System.out.println(first);
        System.out.println(value);
        for (int i = 0; i < value; i++) {
            LocalDate localDate = first.plusDays(i);
            System.out.println(localDate);
        }
    }
}
