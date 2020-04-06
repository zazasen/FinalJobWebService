package com.cyrus.final_job.controller.hr;

import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.service.ContractService;
import com.cyrus.final_job.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hr/contract")
public class HrContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/getAllContract")
    public ResultPage getAllContract(@RequestBody JSONObject params) {
        return contractService.getAllContract(params);
    }

    @GetMapping("/getContract")
    public String getContract(@RequestParam("userId") Integer userId) {
        return contractService.getContract(userId);
    }

    @GetMapping("/getAllDepartment")
    public Result getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @PostMapping("/addContract")
    public Result addContract(@RequestBody JSONObject params) {
        return contractService.addContract(params);
    }

    /**
     * 续签
     * @param params
     * @return
     */
    @PostMapping("/renewalContract")
    public Result renewalContract(@RequestBody JSONObject params) {
        return contractService.renewalContract(params);
    }
}
