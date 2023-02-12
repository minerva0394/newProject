package com.zzl.newprojectspring.StudentService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzl.newprojectspring.entity.Student;
import com.zzl.newprojectspring.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends ServiceImpl<StudentMapper,Student> {

    public boolean saveStudent(Student student){
        //mybatis-plus 提供的单表插入方法
        return saveOrUpdate(student);
    }

}
