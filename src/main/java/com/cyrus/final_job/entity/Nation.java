package com.cyrus.final_job.entity;

import java.io.Serializable;

/**
 * 民族表(Nation)实体类
 *
 * @author cyrus
 * @since 2020-02-20 15:32:37
 */
public class Nation implements Serializable {
    private static final long serialVersionUID = 566141022534368250L;
    /**
    * 民族id
    */
    private Integer id;
    /**
    * 民族名称
    */
    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}