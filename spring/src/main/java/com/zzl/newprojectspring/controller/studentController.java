package com.zzl.newprojectspring.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zzl.newprojectspring.StudentService.StudentService;
import com.zzl.newprojectspring.entity.Student;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@Api(tags = "学生管理")
@RequestMapping("/student")

/**
 * 学生表相关操作接口，与其他用户表独立
 */
public class studentController {


    @Autowired
    private StudentService studentService;

    /**
     * 查询接口
     *
     * @return
     */
    @ApiOperation(value = "查询所有学生")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<Student> findAll() {
        return studentService.list();
    }

    /**
     * 实现保存和新增
     *
     * @param student
     * @return
     */
    @ApiOperation(value = "实现保存和新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public boolean save(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    /**
     * studentNo
     *
     * @param studentNo
     * @return
     */
    @ApiOperation(value = "通过studentNo删除")
    @RequestMapping(value = "/delete/{studentNo}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable Integer studentNo) {
        return studentService.removeById(studentNo);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public boolean batchDelete(@RequestBody List<Integer> ids) {
        return studentService.removeBatchByIds(ids);
    }

    /**
     * 实现分页查询-mybatis-plus
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Integer", name = "pageNum", value = "页码", required = false),
            @ApiImplicitParam(dataType = "Integer", name = "pageSize", value = "每页数量", required = false),
            @ApiImplicitParam(name = "studentName", value = "学生姓名", required = false),
            @ApiImplicitParam(name = "studentNo", value = "学生学号", required = false),
            @ApiImplicitParam(name = "studentSex", value = "学生性别", required = false),
            @ApiImplicitParam(name = "studentMajor", value = "学生专业", required = false)
    })
    public IPage<Student> findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam String studentName,
                                   @RequestParam String studentNo,
                                   @RequestParam String studentSex,
                                   @RequestParam String studentMajor) {
        IPage<Student> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if (!"".equals(studentNo)) {
            queryWrapper.like("studentNo", studentNo);
        }
        if (!"".equals(studentSex)) {
            queryWrapper.like("studentSex", studentSex);
        }
        if (!"".equals(studentName)) {
            queryWrapper.like("studentName", studentName);
        }
        if (!"".equals(studentMajor)) {
            queryWrapper.like("studentMajor", studentMajor);
        }
        return studentService.page(page, queryWrapper);
    }

    /**
     * 学生表导出
     *
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "信息导出")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletResponse response) throws Exception {
        List<Student> list = studentService.list();
        //导出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 设置列宽
        writer.setColumnWidth(0, 10);
        writer.setColumnWidth(4, 18);
        writer.setColumnWidth(5, 18);
        //自定义标题别名
        writer.addHeaderAlias("studentNo", "学生学号");
        writer.addHeaderAlias("studentPassword", "密码");
        writer.addHeaderAlias("studentName", "学生姓名");
        writer.addHeaderAlias("studentSex", "学生性别");
        writer.addHeaderAlias("studentCollege", "学院");
        writer.addHeaderAlias("studentMajor", "专业");
        writer.addHeaderAlias("studentGpa", "学生绩点");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("deleted", "是否存在");

        //一次性写入到excel
        writer.write(list, true);
        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("学生用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    /**
     * 模板下载接口
     * @param response
     * @throws Exception
     */
    @ApiModelProperty(value = "下载模板")
    @RequestMapping(value = "/export/template")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        List<String> row1 = CollectionUtil.newArrayList("studentNo", "studentPassword", "studentName", "studentSex",
                "studentCollege","studentMajor","studentGpa");
        List<List<String>> rows = CollectionUtil.newArrayList(Collections.singleton(row1));
        //导出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 设置列宽
        writer.autoSizeColumnAll();

        //一次性写入到excel
        writer.write(rows, true);
        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("学生用户信息模板", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    /**
     * 学生信息导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @ApiModelProperty(value = "学生信息导入接口")
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public Boolean importStudent(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);

        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<Student> list = reader.readAll(Student.class);

        studentService.saveBatch(list);
        return true;

    }

}
