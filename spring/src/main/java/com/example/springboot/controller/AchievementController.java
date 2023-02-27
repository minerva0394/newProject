package com.example.springboot.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.UserDto;
import com.example.springboot.entity.Achievement;
import com.example.springboot.entity.Role;
import com.example.springboot.entity.User;
import com.example.springboot.service.IAchievementService;
import com.example.springboot.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器,实体类
 * </p>
 *
 * @author zengzl
 */
@RestController
@RequestMapping("/achievement")
public class AchievementController {

    @Resource
    private IAchievementService achievementService;

    /**
     * 返回所有用户信息
     *
     * @return
     */
    @GetMapping
    @ApiOperation("返回所有用户信息")
    public Result findAll() {
        return Result.success(achievementService.list());
    }

    /**
     * 给前端传值
     *
     * @param username
     * @param pageSize
     * @return
     */
    @ApiOperation("根据ID查询学生成绩")
    @RequestMapping(path = "/page/{username}",method = RequestMethod.GET)
    public Result findPage(@PathVariable String username,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Achievement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_number", username);
        return Result.success(achievementService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }





}

