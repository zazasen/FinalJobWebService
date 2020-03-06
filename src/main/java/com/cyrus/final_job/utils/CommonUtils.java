package com.cyrus.final_job.utils;

import com.iceyyy.workday.WorkUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    public static String getPassword(String username) {
        return new BCryptPasswordEncoder().encode(username);
    }

    public static long doubleToLong(Double d) {
        long l = new Double(d).longValue();
        return l;
    }

    public static int doubleToInt(Double d) {
        int i = new Double(d).intValue();
        return i;
    }

    /**
     * @param name 汉字
     * @param full 是否全拼
     * @return res
     */
    public static String convertHanzi2Pinyin(String name, boolean full) {
        /***
         * ^[\u2E80-\u9FFF]+$ 匹配所有东亚区的语言
         * ^[\u4E00-\u9FFF]+$ 匹配简体和繁体
         * ^[\u4E00-\u9FA5]+$ 匹配简体
         */
        String regExp = "^[\u4E00-\u9FFF]+$";
        StringBuffer sb = new StringBuffer();
        if (name == null || "".equals(name.trim())) {
            return "";
        }
        String pinyin = "";
        for (int i = 0; i < name.length(); i++) {
            char unit = name.charAt(i);
            if (match(String.valueOf(unit), regExp))//是汉字，则转拼音
            {
                pinyin = convertSingleHanzi2Pinyin(unit);
                if (full) {
                    sb.append(pinyin);
                } else {
                    sb.append(pinyin.charAt(0));
                }
            } else {
                sb.append(unit);
            }
        }
        return sb.toString();
    }

    private static String convertSingleHanzi2Pinyin(char name) {
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String[] res;
        StringBuffer sb = new StringBuffer();
        try {
            res = PinyinHelper.toHanyuPinyinStringArray(name, outputFormat);
            sb.append(res[0]);//对于多音字，只用第一个拼音
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return sb.toString();
    }

    private static boolean match(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static Integer shouldBeWorkDays() {
        LocalDate now = LocalDate.now();
        // 本月第一天
        LocalDate first = now.with(TemporalAdjusters.firstDayOfMonth());
        // 本月最后一天
        LocalDate last = now.with(TemporalAdjusters.lastDayOfMonth());
        // 本月天数
        int value = last.getDayOfMonth();
        // 本月应该出勤天数
        int shouldBeWorkDays = 0;
        // 遍历每一天
        for (int i = 0; i < value; i++) {
            LocalDate localDate = first.plusDays(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String format = formatter.format(localDate);
            boolean isHoliday = WorkUtils.isWorkendDay(format);
            if (!isHoliday) {
                shouldBeWorkDays++;
            }
        }
        return shouldBeWorkDays;
    }

    public static Boolean FreeDayJudge(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String format = formatter.format(localDate);
        return WorkUtils.isWorkendDay(format);
    }
}
