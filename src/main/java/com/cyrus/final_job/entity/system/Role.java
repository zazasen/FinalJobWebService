package com.cyrus.final_job.entity.system;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 角色表(Role)实体类
 *
 * @author cyrus
 * @since 2020-02-16 14:53:32
 */
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 487262019744191868L;

    private Integer id;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色中文名称
     */
    private String roleZh;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;


    public Result checkParams() {
        if (StringUtils.isEmpty(this.roleName)) {
            return Results.error("角色名不能为空");
        }
        if (StringUtils.isEmpty(this.roleZh)) {
            return Results.error("角色中文名不能为空");
        }
        if(!this.roleName.startsWith("ROLE_")){
            this.roleName = "ROLE_" + this.roleName;
        }
        return null;
    }

}