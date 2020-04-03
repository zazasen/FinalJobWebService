package com.cyrus.final_job.entity.vo;

import com.cyrus.final_job.entity.Contract;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ContractVo extends Contract {

    private String realName;
    private Long workID;
    private String departmentName;
    private String phone;
    private String email;
    private String confirmStr;
    private Integer state;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Timestamp beginContractTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Timestamp endContractTime;
}
