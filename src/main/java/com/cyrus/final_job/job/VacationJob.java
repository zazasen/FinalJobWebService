package com.cyrus.final_job.job;


import com.cyrus.final_job.dao.HolidayDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.Holiday;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.enums.EnableBooleanEnum;
import com.cyrus.final_job.enums.GenderEnum;
import com.cyrus.final_job.enums.HolidayTypeEnum;
import com.cyrus.final_job.enums.WedlockEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
public class VacationJob {

    @Autowired
    private HolidayDao holidayDao;

    @Autowired
    private UserDao userDao;


    public void buildHoliday() {

        List<User> users = userDao.selectAll();

        for (User user : users) {
            if (Objects.equals(EnableBooleanEnum.DISABLE.getCode(), user.isEnabled())) {
                continue;
            }

            holidayDao.deleteByUserId(user.getId());
            Holiday holiday = new Holiday();
            // 初始化调休假期
            holiday.setUserId(user.getId());
            holiday.setHolidayType(HolidayTypeEnum.EXCHANGE.getCode());
            holiday.setHolidayTime(0);
            holiday.setRemaining(0);
            holiday.setCreateTime(String.valueOf(LocalDate.now().getYear()));
            holidayDao.insert(holiday);
            // 初始化病假
            holiday.setHolidayType(HolidayTypeEnum.SICK_LEAVE.getCode());
            holiday.setHolidayTime(5);
            holiday.setRemaining(5);
            holiday.setCreateTime(String.valueOf(LocalDate.now().getYear()));
            holidayDao.insert(holiday);
            // 初始化年假，工龄0-5年休5天，5-10年修10天，10-20年修15天，20-无穷大，休20天
            holiday.setHolidayType(HolidayTypeEnum.ANNUAL_LEAVE.getCode());
            if (user.getWorkAge() >= 0 && user.getWorkAge() <= 5) {
                holiday.setHolidayTime(5);
                holiday.setRemaining(5);
            } else if (user.getWorkAge() > 5 && user.getWorkAge() <= 10) {
                holiday.setHolidayTime(10);
                holiday.setRemaining(10);
            } else if (user.getWorkAge() > 10 && user.getWorkAge() <= 20) {
                holiday.setHolidayTime(15);
                holiday.setRemaining(15);
            } else if (user.getWorkAge() > 20) {
                holiday.setHolidayTime(20);
                holiday.setRemaining(20);
            } else {
                holiday.setHolidayTime(0);
                holiday.setRemaining(0);
            }
            holiday.setCreateTime(String.valueOf(LocalDate.now().getYear()));
            holidayDao.insert(holiday);
            // 初始化哺乳假，女方两个月，男方一礼拜
            holiday.setHolidayType(HolidayTypeEnum.BREASTFEEDING_LEAVE.getCode());
            if (Objects.equals(GenderEnum.WOMAN.getCode(), user.getGender())) {
                holiday.setHolidayTime(60);
                holiday.setRemaining(60);
            } else if (Objects.equals(GenderEnum.MAN.getCode(), user.getGender())) {
                holiday.setHolidayTime(7);
                holiday.setRemaining(7);
            } else {
                holiday.setHolidayTime(0);
                holiday.setRemaining(0);
            }
            holiday.setCreateTime(String.valueOf(LocalDate.now().getYear()));
            holidayDao.insert(holiday);
            // 初始化婚假，针对未结婚员工
            holiday.setHolidayType(HolidayTypeEnum.MARRIAGE_HOLIDAY.getCode());
            if (!Objects.equals(WedlockEnum.MARRIED.getCode(), user.getWedlock())) {
                holiday.setHolidayTime(7);
                holiday.setRemaining(7);
            } else {
                holiday.setHolidayTime(0);
                holiday.setRemaining(0);
            }
            holiday.setCreateTime(String.valueOf(LocalDate.now().getYear()));
            holidayDao.insert(holiday);
            // 初始化丧假
            holiday.setHolidayType(HolidayTypeEnum.FUNERAL_LEAVE.getCode());
            holiday.setHolidayTime(7);
            holiday.setRemaining(7);
            holiday.setCreateTime(String.valueOf(LocalDate.now().getYear()));
            holidayDao.insert(holiday);
        }
    }
}
