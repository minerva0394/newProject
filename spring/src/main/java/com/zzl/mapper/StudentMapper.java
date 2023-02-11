package com.zzl.mapper;

import com.zzl.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {

    /**
     * 查询所有用户
     */
    @Select("select * from student")
    List<Student> findAll();
}
