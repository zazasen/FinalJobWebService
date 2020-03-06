package com.cyrus.final_job.job;

import com.cyrus.final_job.dao.CheckInDao;
import com.cyrus.final_job.dao.LeaveDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.CheckIn;
import com.cyrus.final_job.entity.Leave;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.enums.SignInTypeEnum;
import com.cyrus.final_job.enums.SignTypeEnum;
import com.cyrus.final_job.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component()
public class CheckInJob {


    @Autowired
    private CheckInDao checkInDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LeaveDao leaveDao;

    public void buildCheckIn() {
        User user = new User();
        user.setEnabled(true);
        List<User> users = userDao.queryAll(user);
        // 更新昨天的考勤数据，如果有人没有补卡，则为其补上缺卡记录
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
//        LocalDateTime yesterday = LocalDateTime.now().plusDays(1);

        Boolean isFreeDay = CommonUtils.FreeDayJudge(yesterday.toLocalDate());
        if (isFreeDay) {
            return;
        }

        // 遍历每个在职员工
        for (User item : users) {
            // 如果昨天有考勤，直接判断下一个员工
            CheckIn condition = new CheckIn();
            condition.setUserId(item.getId());
            condition.setEnabled(true);
            List<CheckIn> checkIns = checkInDao.queryAll(condition);
            if (!CollectionUtils.isEmpty(checkIns)) {
                continue;
            }
            // 判断昨天员工是否有请假
            Leave leave = new Leave();
            leave.setUserId(item.getId());
            leave.setEnabled(true);
            // 是否有请假变量
            boolean free = false;
            // 获取该员工所有假期
            List<Leave> leaves = leaveDao.queryAll(leave);
            for (Leave leaf : leaves) {
                LocalDateTime begin = leaf.getBeginTime().toLocalDateTime();
                LocalDateTime end = leaf.getEndTime().toLocalDateTime();
                // 昨天请假了
                if (yesterday.isBefore(end) && yesterday.isAfter(begin)) {
                    free = true;
                    CheckIn checkIn = buildCheckIn(item, free);
                    checkInDao.insert(checkIn);
                    break;
                }
            }
            if (free == false) {
                CheckIn checkIn = buildCheckIn(item, free);
                checkInDao.insert(checkIn);
            }
        }
    }

    private CheckIn buildCheckIn(User user, boolean free) {
        CheckIn checkIn = new CheckIn();
        checkIn.setUserId(user.getId());
        checkIn.setStartType(SignInTypeEnum.WITHOUT_SIGN.getCode());
        checkIn.setEndType(SignInTypeEnum.WITHOUT_SIGN.getCode());
        if (free) {
            checkIn.setSignType(SignTypeEnum.FREE.getCode());
        } else {
            checkIn.setSignType(SignTypeEnum.NONE.getCode());
        }
        checkIn.setCreateTime(LocalDate.now().minusDays(1).toString());
        checkIn.setEnabled(true);
        return checkIn;
    }
}
