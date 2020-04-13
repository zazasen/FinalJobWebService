package com.cyrus.final_job.entity;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.vo.ApprovalFlowVo;
import com.cyrus.final_job.utils.Results;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门表(Department)实体类
 *
 * @author cyrus
 * @since 2020-02-19 13:34:24
 */
@Data
public class Department implements Serializable {
    private static final long serialVersionUID = 655848856536477699L;

    private Integer id;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 上级部门id
     */
    private Integer parentId;

    private Integer userId;
    private String userRealName;

    private Integer enabled;

    private Integer isParent;

    private String parentName;

    private Integer sonCount;

    private ApprovalFlowVo approvalFlowVo;

    private List<Department> children = new ArrayList<>();

    public Result checkParams() {
        if (name == null) {
            return Results.error("部门名不能为空");
        }
        if (parentId == null) {
            return Results.error("上级部门id不能为空");
        }
        if (parentName == null) {
            return Results.error("上级部门名不能为空");
        }
        this.enabled = 1;
        this.isParent = 0;
        return null;
    }
}