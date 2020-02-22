package com.cyrus.final_job.controller.hr;


import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.NationService;
import com.cyrus.final_job.service.PoliticsStatusService;
import com.cyrus.final_job.service.PositionService;
import com.cyrus.final_job.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 员工管理 controller
 */

@RestController
@RequestMapping("/hr/staff")
public class StaffManagerController {

    @Autowired
    private UserService userService;

    @Autowired
    private NationService nationService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private PoliticsStatusService politicsStatusService;

    @PostMapping("/getStaff")
    public ResultPage getStaff(@RequestBody JSONObject params) {
        return userService.getStaff(params);
    }

    @GetMapping("/getNations")
    public Result getNations(){
        return nationService.getNations();
    }

    @GetMapping("/getPositions")
    public Result getPositions(){
        return positionService.getPositions();
    }

    @GetMapping("/getPoliticsStatus")
    public Result getPoliticsStatus(){
        return politicsStatusService.getPoliticsStatus();
    }

    @PostMapping("/addStaff")
    public Result addStaff(@RequestBody JSONObject params) {
        return userService.addStaff(params);
    }

    @PostMapping("/updateStaff")
    public Result updateStaff(@RequestBody JSONObject params){
        return userService.updateStaff(params);
    }

    @PostMapping("/delStaff")
    public Result delStaff(@RequestBody JSONObject params){
        return userService.delStaff(params);
    }

    @PostMapping("/delStaffs")
    public Result delStaffs(@RequestBody JSONObject params){
        return userService.delStaffs(params);
    }

    @GetMapping("/exportStaff")
    public ResponseEntity<byte[]> exportStaff(){
        return userService.exportStaff();
    }

    @PostMapping("/importStaff")
    public Result importStaff(MultipartFile file){
        return userService.importStaff(file);
    }

}
