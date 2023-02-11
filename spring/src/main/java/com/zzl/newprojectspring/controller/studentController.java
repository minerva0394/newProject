package com.zzl.newprojectspring.controller;

import com.zzl.newprojectspring.StudentService.StudentService;
import com.zzl.newprojectspring.entity.Student;
import com.zzl.newprojectspring.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/student")

/**
 * 学生表相关操作接口，与其他用户表独立
 */
public class studentController {

    @Resource
    private StudentMapper studentMapper;

    @Autowired
    private StudentService studentService;

    /**
     * 查询接口
     * @return
     */
    @GetMapping
    public List<Student> index() {
        List<Student> all = studentMapper.findAll();
        return all;
    }

    /**
     * 实现保存和新增
     * @param student
     * @return
     */
    @PostMapping
    public Integer save(@RequestBody Student student){
        return studentService.save(student);
    }

    /**
     * 删除id
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id){
        return studentMapper.deleteById(id);
    }
}
