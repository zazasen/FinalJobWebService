package com.cyrus.final_job.utils;

public class RedisKeys {

    public static String shouldBeWorkDaysKey(Integer month) {
        return month + ":";
    }

}
