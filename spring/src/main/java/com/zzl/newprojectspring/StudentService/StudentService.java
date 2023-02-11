package com.zzl.newprojectspring.StudentService;

import com.zzl.newprojectspring.entity.Student;
import com.zzl.newprojectspring.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public int save(Student student) {
        if (student.getId() == null) {  //无当前id，则为新增
            return studentMapper.insert(student);
        } else {    //有当前id，则修改
            return studentMapper.update(student);
        }
    }
}
