package com.cyrus.final_job.controller.staff;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.service.ContractService;
import com.cyrus.final_job.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff/myContract")
public class StaffMyContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping("/getMyContract")
    public Result getMyContract() {
        return contractService.getMyContract();
    }

    @GetMapping("/getContract")
    public String getContract() {
        return contractService.getContract(UserUtils.getCurrentUserId());
    }

    @PostMapping("/confirmAdd")
    public Result confirmAdd() {
        return contractService.confirmAdd();
    }

}
