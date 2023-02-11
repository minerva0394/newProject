package com.zzl.controller;

import com.zzl.entity.Student;
import com.zzl.mapper.StudentMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class studentController {

    @Resource
    private StudentMapper studentMapper;

    @GetMapping("/")
    public List<Student> index() {
        List<Student> all = studentMapper.findAll();
        return all;
    }
}
