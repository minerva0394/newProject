package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 *
 * </p>
 *
 * @author zengzl
 */
@Getter
@Setter
@TableName("sys_user")
@ToString
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("学院")
    private String college;

    @ApiModelProperty("专业")
    private String major;

    @ApiModelProperty("创建时间")
    @TableField(value = "createTime")
    private Date createTime;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("是否删除")
    @TableLogic
    private String deleted;

    @ApiModelProperty("角色")
    private String role;

    @ApiModelProperty("绩点")
    private double gpa1st;

    @ApiModelProperty("第二学期绩点")
    private double gpa2nd;

    @ApiModelProperty("第三学期绩点")
    private double gpa3rd;

    @ApiModelProperty("第四学期绩点")
    private double gpa4th;

    @ApiModelProperty("第五学期绩点")
    private double gpa5th;

    @ApiModelProperty("第六学期绩点")
    private double gpa6th;

    @ApiModelProperty("第七学期绩点")
    private double gpa7th;

    @ApiModelProperty("第八学期绩点")
    private double gpa8th;




}
