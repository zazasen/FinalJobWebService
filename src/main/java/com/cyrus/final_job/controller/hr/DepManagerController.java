package com.cyrus.final_job.controller.hr;


import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.DepartmentService;
import com.cyrus.final_job.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 部门管理 controller
 */
@RestController
@RequestMapping("/hr/department")
public class DepManagerController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @GetMapping("/getAllDepartment")
    public Result getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @PostMapping("/updateDep")
    public Result updateDep(@RequestBody JSONObject params) {
        return departmentService.updateDep(params);
    }

    @PostMapping("/addDep")
    public Result addDep(@RequestBody JSONObject params){
        return departmentService.addDep(params);
    }

    @PostMapping("/delDep")
    public Result delDep(@RequestBody JSONObject params){
        return departmentService.delDep(params);
    }

    @PostMapping("/getDepStaff")
    public Result getDepStaff(@RequestBody JSONObject params){
        return departmentService.getDepStaff(params);
    }
}
