package com.cyrus.final_job.controller.hr;


import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 职位管理 controller
 */

@RestController
@RequestMapping("/hr/position")
public class PositionManagerController {

    @Autowired
    private PositionService positionService;

    @GetMapping("/getLevel")
    public Result getLevel() {
        return positionService.getLevel();
    }

    @PostMapping("/getPosition")
    public ResultPage getPosition(@RequestBody JSONObject params) {
        return positionService.getPosition(params);
    }

    @PostMapping("/addPosition")
    public Result addPosition(@RequestBody JSONObject params) {
        return positionService.addPosition(params);
    }

    @PostMapping("/updatePosition")
    public Result updatePosition(@RequestBody JSONObject params) {
        return positionService.updatePosition(params);
    }

    @PostMapping("/delPosition")
    public Result delPosition(@RequestBody JSONObject params) {
        return positionService.delPosition(params);
    }

    @PostMapping("/delPositionsByIds")
    public Result delPositionsByIds(@RequestBody JSONObject params){
        return positionService.delPositionsByIds(params);
    }

}
