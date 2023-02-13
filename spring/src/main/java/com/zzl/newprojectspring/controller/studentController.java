package com.zzl.newprojectspring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzl.newprojectspring.StudentService.StudentService;
import com.zzl.newprojectspring.entity.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/save",method = RequestMethod.POST)
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
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/batchDelete",method = RequestMethod.POST)
    public boolean batchDelete(@RequestBody List<Integer> ids){
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
        IPage<Student> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if(!"".equals(studentNo)){
            queryWrapper.like("studentNo",studentNo);
        }
        if(!"".equals(studentSex)){
            queryWrapper.like("studentSex",studentSex);
        }
        if(!"".equals(studentName)){
            queryWrapper.like("studentName",studentName);
        }
        if(!"".equals(studentMajor)){
            queryWrapper.like("studentMajor",studentMajor);
        }
        return studentService.page(page,queryWrapper);
    }
}
