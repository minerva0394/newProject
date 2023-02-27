package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zengzl
 */
@Getter
@Setter
@TableName("stu_Achievement")
@ToString
@ApiModel(value = "成绩对象", description = "")
public class Achievement implements Serializable {

    //对应user表里的username
    @ApiModelProperty("学号")
    @TableField("stu_number")
    private String stuNumber;

    @ApiModelProperty("学年")
    private String academicYear;

    @ApiModelProperty("学期")
    private Integer semester;

    @ApiModelProperty("学分")
    private double credit;

    @ApiModelProperty("绩点")
    private double performancePoint;

    @ApiModelProperty("成绩")
    private double achievement;


}
