package com.zzl.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 学生表
 */
@Data
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生id，唯一
     */
    private Integer id;

    /**
     * 学生学号，要唯一，不能重复
     */
    private String studentNo;

    /**
     * 学生登录密码
     */
    private String studentPassword;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生性别
     */
    private String studentSex;

    /**
     * 学院
     */
    private String studentCollege;

    /**
     * 专业
     */
    private String studentMajor;

    /**
     * 学生绩点
     */
    private Double studentGpa;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

}
