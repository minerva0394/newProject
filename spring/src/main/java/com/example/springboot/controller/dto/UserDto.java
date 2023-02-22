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

    private String token;
    private List<Menu> menus;
    private String role;

}
