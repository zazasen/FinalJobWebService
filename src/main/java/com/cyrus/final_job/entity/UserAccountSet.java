package com.cyrus.final_job.entity;

import lombok.Data;

/**
 * 员工账套表(UserAccountSet)实体类
 *
 * @author cyrus
 * @since 2020-03-06 10:01:20
 */

@Data
public class UserAccountSet {

    /**
     * 薪资账套表id
     */
    private Integer id;
    /**
     * user表id
     */
    private Integer userId;
    /**
     * 薪资账套表id
     */
    private Integer accountSetId;
}