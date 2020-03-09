package com.cyrus.final_job.job;

import com.cyrus.final_job.dao.CheckInDao;
import com.cyrus.final_job.entity.CheckIn;
import com.cyrus.final_job.entity.condition.CheckInCondition;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.RedisKeys;
import com.cyrus.final_job.utils.RedisUtil;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class CheckInExceptionJob {

    @Autowired
    private CheckInDao checkInDao;

    @Autowired
    private RedisUtil redisUtil;

    public void build() {
        int userId = UserUtils.getCurrentUserId();
        CheckInCondition condition = new CheckInCondition();
        condition.setUserId(userId);
        condition.setBeginTime(DateUtils.getCurrentMonthFirstDay().toString());
        condition.setTailTime(DateUtils.getCurrentMonthLasterDay().toString());
        List<CheckIn> checkIns = checkInDao.queryAllByConditionNoPage(condition);
        int later = 0;
        int early = 0;
        double workedTime = 0;
        for (CheckIn checkIn : checkIns) {
            // 如果当天上班打卡时间早于9点，算迟到
            if (checkIn.getStartTime() != null && checkIn.getStartTime().toLocalDateTime().isAfter(
                    LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(),
                            LocalDate.now().getDayOfMonth(), 9, 00))) {
                later++;
            }

            // 如果当天下班打卡时间早于下午6点算早退
            if (checkIn.getEndTime() != null && checkIn.getEndTime().toLocalDateTime().isBefore(
                    LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(),
                            LocalDate.now().getDayOfMonth(), 18, 00))) {
                early++;
            }
            if (checkIn.getWorkHours() != null) {
                workedTime = workedTime + checkIn.getWorkHours();
            }
        }

        int month = LocalDate.now().getMonth().getValue();
        String key = RedisKeys.monthStatistics(month);

        Map<String, String> map = new LinkedHashMap<>();
        map.put("早退", String.valueOf(early));
        map.put("迟到", String.valueOf(later));
        map.put("已工作工时", String.valueOf(workedTime));
        redisUtil.hPutAll(key, map);
    }
}
