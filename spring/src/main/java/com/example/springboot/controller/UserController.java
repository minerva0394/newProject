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
import com.example.springboot.entity.User;
import com.example.springboot.service.IUserService;
import com.example.springboot.utils.TokenUtils;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.parser.Token;
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
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 用户登录
     *
     * @param userDto
     * @return
     */
    @ApiOperation("用户登录")
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        UserDto dto = userService.login(userDto);
        return Result.success(dto);
    }

    /**
     * 用户注册
     *
     * @param userDto
     * @return
     */
    @ApiOperation("用户注册")
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public Result register(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userService.register(userDto));
    }

    /**
     * 新增或更新
     *
     * @param user
     * @return
     */
    @PostMapping
    @ApiOperation("新增或更新用户信息")
    public Result save(@RequestBody User user) {
        return Result.success(userService.saveOrUpdate(user));
    }

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    @ApiOperation("删除用户信息")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(userService.removeById(id));
    }

    /**
     * 多用户删除
     *
     * @param ids
     * @return
     */
    @ApiOperation("批量删除")
    @RequestMapping(path = "/del/batch", method = RequestMethod.POST)
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(userService.removeByIds(ids));
    }

    /**
     * 返回所有用户信息
     *
     * @return
     */
    @GetMapping
    @ApiOperation("返回所有用户信息")
    public Result findAll() {
        return Result.success(userService.list());
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @ApiOperation("根据ID查询用户")
    @RequestMapping(path = "/{id}",method = RequestMethod.GET)
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    /**
     * 给前端传值
     *
     * @param username
     * @return
     */
    @ApiOperation("根据ID查询用户")
    @RequestMapping(path = "/username/{username}",method = RequestMethod.GET)
    public Result findOne(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return Result.success(userService.getOne(queryWrapper));
    }

    /**
     * 查找用户信息，实现分页功能
     *
     * @param pageNum
     * @param pageSize
     * @param nickname
     * @return
     */
    @ApiOperation("查找用户信息，实现分页功能")
    @RequestMapping(path = "/page",method = RequestMethod.GET)
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String username,
                           @RequestParam(defaultValue = "") String nickname,
                           @RequestParam(defaultValue = "") String major
    ) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc("id");
        if (!"".equals(nickname)) {
            queryWrapper.like("nickname", nickname);
        }
        if (!"".equals(username)) {
            queryWrapper.like("username", username);
        }
        if (!"".equals(major)) {
            queryWrapper.like("major", major);
        }

        // 获取当前用户信息
//        User currentUser = TokenUtils.getCurrentUser();
//        System.out.println("\n当前用户信息:" + currentUser.getNickname());

        return Result.success(userService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /**
     * 用户信息导出接口
     *
     * @param response
     * @throws Exception
     */
    @ApiOperation("用户信息导出")
    @RequestMapping(path = "/export",method = RequestMethod.GET)
    public void export(HttpServletResponse response) throws Exception {
        // 查询所有数据
        List<User> list = userService.list();
        // 内存写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("id", "id");
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("college", "学院");
        writer.addHeaderAlias("major", "专业");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("avatar", "头像");
        writer.addHeaderAlias("deleted", "是否删除");
        // 一次性将数据写到Excel
        writer.write(list, true);
        //列宽自适应
        writer.autoSizeColumnAll();
        // 设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * 模板导出接口
     *
     * @param response
     * @throws Exception
     */
    @ApiOperation("用户信息导出")
    @RequestMapping(path = "/exportExample",method = RequestMethod.GET)
    public void exportExample(HttpServletResponse response) throws Exception {
        // 查询所有数据
        List<User> list = new ArrayList<>();
        // 内存写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        Map<String, Object> rowHead = new LinkedHashMap<>();
        rowHead.put("username", "此处输入学生/老师的学号");
        rowHead.put("password", "此处输入学生/老师的密码");
        rowHead.put("nickname", "此处输入学生/老师的姓名");
        rowHead.put("email", "此处输入学生/老师姓名的邮箱");
        rowHead.put("phone", "此处输入学生/老师姓名的电话");
        rowHead.put("college", "此处输入学生/老师姓名的学院");
        rowHead.put("major", "此处输入学生/老师姓名的专业");
        ArrayList<Map<String, Object>> rows = CollUtil.newArrayList(rowHead);
        // 一次性将数据写到Excel
        writer.write(rows, true);
        //列宽自适应
        writer.autoSizeColumnAll();
        // 设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("导入模板", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * 用户信息导入接口
     *
     * @param file
     * @throws Exception
     */
    @ApiOperation("用户信息导入")
    @RequestMapping(path = "/import",method = RequestMethod.POST)
    public Result importUserInfo(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);
        userService.saveBatch(list);
        return Result.success(true);
    }

}

