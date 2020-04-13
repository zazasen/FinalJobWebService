package com.cyrus.final_job.controller.hr;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.DepartmentService;
import com.cyrus.final_job.service.ReserveStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hr/reserveStaff")
public class ReserveStaffController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ReserveStaffService reserveStaffService;

    @GetMapping("/getAllDepartment")
    public Result getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @PostMapping("/getAllReserveStaff")
    public ResultPage getAllReserveStaff(@RequestBody JSONObject params) {
        return reserveStaffService.getAllReserveStaff(params);
    }

    @PostMapping("/addReserveStaff")
    public Result addReserveStaff(@RequestBody JSONObject params) {
        return reserveStaffService.addReserveStaff(params);
    }

    @PostMapping("/updateReserveStaff")
    public Result updateReserveStaff(@RequestBody JSONObject params) {
        return reserveStaffService.updateReserveStaff(params);
    }

    @PostMapping("delReserveStaff")
    public Result delReserveStaff(@RequestBody JSONObject params){
        return reserveStaffService.delReserveStaff(params);
    }

    @PostMapping("delReserveStaffs")
    public Result delReserveStaffs(@RequestBody JSONObject params){
        return reserveStaffService.delReserveStaffs(params);
    }
}
