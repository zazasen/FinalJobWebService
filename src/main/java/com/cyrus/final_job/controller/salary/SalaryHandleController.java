package com.cyrus.final_job.controller.salary;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.condition.TimeRangeCondition;
import com.cyrus.final_job.service.DepartmentService;
import com.cyrus.final_job.service.SalaryService;
import com.cyrus.final_job.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/salary/handle")
public class SalaryHandleController {

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/salaryHandle")
    public Result salaryHandle() {
        return salaryService.salaryHandle();
    }

    @GetMapping("/getAllDepartment")
    public Result getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @PostMapping("/getAllSalary")
    public ResultPage getAllSalary(@RequestBody JSONObject params) {
        return salaryService.getAllSalary(params);
    }

    /**
     * 补差价
     * @param params
     * @return
     */
    @PostMapping("/editSalary")
    public Result editSalary(@RequestBody JSONObject params) {
        return salaryService.editSalary(params);
    }

    @PostMapping("/delMul")
    public Result delMul(@RequestBody JSONObject params){
        return salaryService.delMul(params);
    }

    @PostMapping("/delOne")
    public Result delOne(@RequestBody JSONObject params){
        return salaryService.delOne(params);
    }

    @GetMapping("/exportSalaryStatistics")
    public ResponseEntity<byte[]> exportSalaryStatistics(String startTime, String endTime){
        TimeRangeCondition timeRange = new TimeRangeCondition();
        Timestamp start = new Timestamp(Long.valueOf(startTime));
        Timestamp end = new Timestamp(Long.valueOf(endTime));
        timeRange.setStartTime(DateUtils.LocalDate2Timestamp(DateUtils.getMonthFirstDay(start.toLocalDateTime().plusMonths(1L).toLocalDate())));
        timeRange.setEndTime(DateUtils.LocalDate2Timestamp(DateUtils.getMonthFirstDay(end.toLocalDateTime().plusMonths(1L).toLocalDate())));
        return salaryService.exportSalaryStatistics(timeRange);
    }
}
