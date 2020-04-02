package com.cyrus.final_job.entity.condition;

import com.cyrus.final_job.entity.Resume;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.enums.ResumeStatusEnum;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import lombok.Data;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Data
public class InputResumeCondition {
    MultipartFile file;
    String name;
    String phone;
    String idCard;
    String email;
    Integer staffNeedsId;

    public Result checkParams() {
        if (this.file == null) return Results.error("简历不能为空");
        if (StringUtils.isEmpty(this.name)) return Results.error("投递人姓名不能为空");
        if (StringUtils.isEmpty(this.phone)) return Results.error("投递人电话不能为空");
        if (StringUtils.isEmpty(this.idCard)) return Results.error("投递人身份证号码不能为空");
        if (StringUtils.isEmpty(this.email)) return Results.error("投递人邮箱不能为空");
        if (Objects.isNull(this.staffNeedsId)) return Results.error("招聘需求表id不能为空");
        return null;
    }

    public Resume buildResume() {
        Resume resume = new Resume();
        resume.setName(name);
        resume.setPhone(phone);
        resume.setIdCard(idCard);
        resume.setEmail(email);
        resume.setStaffNeedsId(staffNeedsId);
        resume.setStatus(ResumeStatusEnum.SCREENING.getCode());
        resume.setCreateTime(DateUtils.getNowTime());
        return resume;
    }
}
