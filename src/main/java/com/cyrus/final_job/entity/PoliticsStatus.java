package com.cyrus.final_job.entity;

import java.io.Serializable;

/**
 * 政治面貌表(PoliticsStatus)实体类
 *
 * @author cyrus
 * @since 2020-02-20 16:00:31
 */
public class PoliticsStatus implements Serializable {
    private static final long serialVersionUID = 623523132311397995L;
    /**
    * 政治面貌id
    */
    private Integer id;
    /**
    * 政治面貌名称
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