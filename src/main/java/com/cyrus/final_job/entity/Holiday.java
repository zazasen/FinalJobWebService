package com.cyrus.final_job.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 员工假期表(Holiday)实体类
 *
 * @author cyrus
 * @since 2020-03-01 10:36:25
 */

@Data
public class Holiday implements Serializable {
    private static final long serialVersionUID = -73247438810297276L;

    /**
     * 假期表id
     */
    private Integer id;

    /**
     * user 表 id
     */
    private Integer userId;

    /**
     * 假期类型 0 调休 1 带薪病假 2 年假 3 哺乳假 4 婚假 5 丧假
     */
    private Integer holidayType;

    /**
     * 所有假期
     */
    private Integer holidayTime;

    /**
     * 剩余假期
     */
    private Integer remaining;

    /**
     * 创建时间
     */
    private String createTime;
}