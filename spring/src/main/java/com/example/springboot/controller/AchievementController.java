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
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String courseName) {
        QueryWrapper<Achievement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_number", username);
        if(!"".equals(courseName)){
            queryWrapper.like("course_name", courseName);
        }
        return Result.success(achievementService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }


    /**
     * 成绩导出接口
     *
     * @param response
     * @throws Exception
     */
    @ApiOperation("用户信息导出")
    @RequestMapping(path = "/export/{username}",method = RequestMethod.GET)
    public void export(HttpServletResponse response,@PathVariable String username) throws Exception {
        // 查询所有数据
        QueryWrapper<Achievement> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("stu_number",username);
        List<Achievement> list =  achievementService.list(queryWrapper);

        // 内存写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("stuNumber", "学号");
        writer.addHeaderAlias("academicYear", "学年");
        writer.addHeaderAlias("semester", "学期");
        writer.addHeaderAlias("credit", "学分");
        writer.addHeaderAlias("performancePoint", "绩点");
        writer.addHeaderAlias("achievement", "成绩");
        writer.addHeaderAlias("courseName", "课程名");
        writer.addHeaderAlias("teacherName", "教师姓名");
        writer.addHeaderAlias("assessmentMethods", "考核方式");
        // 一次性将数据写到Excel
        writer.write(list, true);
        //列宽自适应
        writer.autoSizeColumnAll();
        // 设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("学生成绩", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }


}

