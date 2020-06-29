package com.cyrus.final_job.service.impl.system;

import com.cyrus.final_job.dao.CheckInDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.CheckIn;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.enums.SignInTypeEnum;
import com.cyrus.final_job.enums.SignTypeEnum;
import com.cyrus.final_job.utils.CommonUtils;
import com.cyrus.final_job.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class BuildCheckInTest {

    @Autowired
    private CheckInDao checkInDao;

    @Autowired
    private UserDao userDao;

    @Test
    public void build() {
        List<User> users = userDao.selectAll();
//            LocalDate lastMonth = LocalDate.now().minusMonths(1);
        LocalDate lastMonth = LocalDate.now();
        LocalDate first = lastMonth.with(TemporalAdjusters.firstDayOfMonth());
//            LocalDate last = lastMonth.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate last = lastMonth;
        for (int i = 0; i < last.getDayOfMonth(); i++) {
            for (User user : users) {
                CheckIn checkIn = new CheckIn();
//            checkIn.setUserId(58);
                checkIn.setUserId(user.getId());
                LocalDate now = first.plusDays(i);
                Boolean aBoolean = CommonUtils.FreeDayJudge(now);
                if(aBoolean){
                    continue;
                }
                LocalDateTime start = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 9, 00);
                Date date = Date.from(start.toInstant(ZoneOffset.of("+8")));
                checkIn.setStartTime(new Timestamp(date.getTime()));
                checkIn.setStartType(SignInTypeEnum.NORMAL.getCode());
                LocalDateTime end = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 20, 00);
                date = Date.from(end.toInstant(ZoneOffset.of("+8")));
                checkIn.setEndTime(new Timestamp(date.getTime()));
                checkIn.setEndType(SignInTypeEnum.NORMAL.getCode());
                checkIn.setSignType(SignTypeEnum.FULL.getCode());
                checkIn.setCreateTime(now.toString());
                checkIn.setWorkHours(DateUtils.getGapTime(start, end));
                checkIn.setEnabled(true);
                checkInDao.insert(checkIn);
            }
        }
    }
}
