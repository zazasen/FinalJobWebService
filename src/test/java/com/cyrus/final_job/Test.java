package com.cyrus.final_job;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 10, 00);
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
