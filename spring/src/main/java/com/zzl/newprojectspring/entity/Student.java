package com.zzl.newprojectspring.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 学生表
 */
@Data
@ApiModel("学生表信息")
@TableName(value = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生id，唯一
     */
    @ApiModelProperty(value = "学生id",required = false)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 学生学号，要唯一，不能重复
     */
    @ApiModelProperty(value = "学生学号",required = false)
    @TableField("studentNo")
    private String studentNo;

    /**
     * 学生登录密码
     */
    @ApiModelProperty(value = "学生登录密码",required = false)
    @TableField("studentPassword")
    private String studentPassword;

    /**
     * 学生姓名
     */
    @ApiModelProperty(value = "学生姓名",required = false)
    @TableField("studentName")
    private String studentName;

    /**
     * 学生性别
     */
    @ApiModelProperty(value = "学生性别",required = false)
    @TableField("studentSex")
    private String studentSex;

    /**
     * 学院
     */
    @ApiModelProperty(value = "学院",required = false)
    @TableField("studentCollege")
    private String studentCollege;

    /**
     * 专业
     */
    @ApiModelProperty(value = "专业",required = false)
    @TableField("studentMajor")
    private String studentMajor;

    /**
     * 学生绩点
     */
    @ApiModelProperty(value = "学生绩点",required = false)
    @TableField("studentGpa")
    private Double studentGpa;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间",required = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("createTime")
    private Timestamp createTime;

}
