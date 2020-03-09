package com.cyrus.final_job.utils;

public class RedisKeys {

    /**
     * 本月应该工作日
     *
     * @param month
     * @return
     */
    public static String shouldBeWorkDaysKey(Integer month) {
        return "shouldBeWork-" + month + ":";
    }

    /**
     * 本月迟到天数
     *
     * @param month
     * @return
     */
    public static String lateKey(Integer month) {
        return "late-" + month + ":";
    }

    /**
     * 早退天数
     * @param month
     * @return
     */
    public static String leaveEarly(Integer month){
        return "leaveEarly" + month + ":";
    }

    /**
     * 本月考勤统计，包括每个月迟到的天数，早退天数，已工作天数
     * @param month
     * @return
     */
    public static String monthStatistics(Integer month){
        return "monthStatistics" + month + ":";
    }
}
