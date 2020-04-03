package com.cyrus.final_job.controller.system;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.CheckInService;
import com.cyrus.final_job.service.LeaveService;
import com.cyrus.final_job.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 需要登录但是不需要指定角色的链接
 */
@RestController
@RequestMapping("/system/config")
public class SystemController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private LeaveService leaveService;

    @GetMapping("/menu")
    public Result getAllMenus() {
        return menuService.getMenusByUserId();
    }

    @GetMapping("/calendarShow")
    public Result calendarShow() {
        return checkInService.calendarShow();
    }

    @GetMapping("/signType")
    public Result signType() {
        return checkInService.signType();
    }

    @PostMapping("/getShouldBeWorkDays")
    public Result getShouldBeWorkDays() {
        return checkInService.getShouldBeWorkDays();
    }

    /**
     * 获取异常考勤,迟到早退情况
     *
     * @return
     */
    @PostMapping("/getExceptionCheckIn")
    public Result getExceptionCheckIn() {
        return checkInService.getExceptionCheckIn();
    }

    @PostMapping("/getLeaveInfo")
    public Result getLeaveInfo() {
        return leaveService.getLeaveInfo();
    }

    /**
     * 签到
     *
     * @return
     */
    @PostMapping("/signIn")
    public Result signIn() {
        return checkInService.signIn();
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/signOut")
    public Result signOut() {
        return checkInService.signOut();
    }
}
