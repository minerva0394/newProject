package com.example.springboot.controller.dto;


import com.example.springboot.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 传输数据,接收前端请求参数
 * </p>
 *
 * @author zengzl
 */
@Data
public class UserDto {
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private double gpa1st;
    private double gpa2nd;
    private double gpa3rd;
    private double gpa4th;
    private double gpa5th;
    private double gpa6th;
    private double gpa7th;
    private double gpa8th;

    private String token;
    private List<Menu> menus;
    private String role;

}
