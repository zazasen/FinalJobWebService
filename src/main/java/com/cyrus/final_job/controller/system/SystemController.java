package com.cyrus.final_job.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.CheckInService;
import com.cyrus.final_job.service.LeaveService;
import com.cyrus.final_job.service.system.MenuService;
import com.cyrus.final_job.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @Autowired
    private UserService userService;

    @PostMapping("/getMyInfo")
    public Result getMyInfo(@RequestBody JSONObject params) {
        return userService.getMyInfo(params);
    }

    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody JSONObject params){
        return userService.updatePassword(params);
    }

    @PostMapping("/updateUserFace")
    public Result updateUserFace(MultipartFile file,Integer id){
        return userService.updateUserFace(file,id);
    }

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
