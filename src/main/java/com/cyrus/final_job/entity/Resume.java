package com.cyrus.final_job.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 简历表(Resume)实体类
 *
 * @author cyrus
 * @since 2020-04-01 14:51:56
 */
@Data
public class Resume {

    /**
     * 简历表id
     */
    private Integer id;
    /**
     * 招聘需求表id
     */
    private Integer staffNeedsId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 简历文件url
     */
    private String url;
    /**
     * 创建时间
     */
    private Timestamp createTime;
}