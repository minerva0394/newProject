package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.controller.dto.UserDto;
import com.example.springboot.entity.Achievement;
import com.example.springboot.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengzl
 *
 */
public interface IAchievementService extends IService<Achievement> {

    Page<Achievement> findPage(Page<Achievement> objectPage, String stuNumber);

}
