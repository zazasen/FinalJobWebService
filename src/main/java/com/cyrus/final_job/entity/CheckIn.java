package com.cyrus.final_job.entity;

import com.cyrus.final_job.enums.SignInTypeEnum;
import com.cyrus.final_job.enums.SignTypeEnum;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.UserUtils;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 签到表(CheckIn)实体类
 *
 * @author cyrus
 * @since 2020-02-25 13:06:39
 */
@Data
public class CheckIn implements Serializable {
    private static final long serialVersionUID = 489104003847732824L;
    /**
     * 签到表id
     */
    private Integer id;
    /**
     * user表id
     */
    private Integer userId;
    /**
     * 上班签到地址
     */
    private String startPlace;
    /**
     * 上班签到时间
     */
    private Timestamp startTime;
    /**
     * 上班签到类型：0 正常签到 1 外勤签到 2 补签 3 未签到 5 周末加班
     */
    private Integer startType;
    /**
     * 下班签到地址
     */
    private String endPlace;
    /**
     * 下班签到时间
     */
    private Timestamp endTime;
    /**
     * 下班签到类型：0 正常签到 1 外勤签到 2 补签 3 未签到 5 周末加班
     */
    private Integer endType;
    /**
     * 打卡状态:0 半天班，1 一天班
     */
    private Integer signType;
    /**
     * 现在是哪一天，该字段和user_id字段定位一个员工当天的记录
     */
    private String createTime;
    /**
     * 上班时长，小时
     */
    private Double workHours;

    /**
     * 上班签到
     *
     * @return
     */
    public static CheckIn signIn() {
        CheckIn checkIn = new CheckIn();
        checkIn.setUserId(UserUtils.getCurrentUserId());
        checkIn.setStartTime(DateUtils.getNowTime());
        checkIn.setStartType(SignInTypeEnum.NORMAL.getCode());
        checkIn.setCreateTime(LocalDate.now().toString());
        checkIn.setSignType(SignTypeEnum.HALF.getCode());
        return checkIn;
    }

    /**
     * 下班签退
     *
     * @param checkIn
     */
    public static CheckIn signOut(CheckIn checkIn) {
        if (checkIn == null) {
            checkIn = new CheckIn();
            checkIn.setId(UserUtils.getCurrentUserId());
            checkIn.setUserId(UserUtils.getCurrentUserId());
            checkIn.setEndType(SignInTypeEnum.NORMAL.getCode());
            checkIn.setEndTime(DateUtils.getNowTime());
            checkIn.setCreateTime(LocalDate.now().toString());
            checkIn.setSignType(SignTypeEnum.HALF.getCode());
            checkIn.setWorkHours(4.0);
            return checkIn;
        } else {
            checkIn.setEndType(SignInTypeEnum.NORMAL.getCode());
            checkIn.setEndTime(DateUtils.getNowTime());
            checkIn.setSignType(SignTypeEnum.FULL.getCode());
            LocalDateTime start = checkIn.getStartTime().toLocalDateTime();
            LocalDateTime end = checkIn.getEndTime().toLocalDateTime();
            checkIn.setWorkHours(DateUtils.getGapTime(start, end));
            return checkIn;
        }
    }

}