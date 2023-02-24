package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

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

    @ApiModelProperty("角色")
    private String role;

    @ApiModelProperty("是否删除")
    @TableLogic
    private String deleted;

    @ApiModelProperty("绩点")
    private float gpa;





}
