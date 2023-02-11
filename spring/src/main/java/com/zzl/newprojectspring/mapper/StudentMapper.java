package com.zzl.newprojectspring.mapper;

import com.zzl.newprojectspring.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    /**
     * 查询所有用户
     */
    @Select("select * from student")
    List<Student> findAll();

    @Insert("insert into newProject.`student` (`id`, `studentNo`, `studentPassword`, `studentName`, `studentSex`, `studentCollege`, `studentMajor`, `studentGpa`) values " +
            "(#{id}, #{studentNo}, #{studentPassword}, #{studentName}, #{studentSex}, #{studentCollege}, #{studentMajor}, #{studentGpa})")
    int insert(Student student);

    /**
     * 更新或修改
     *
     * @param student
     * @return
     */
    int update(Student student);

    /**
     * 删除
     * @param id
     * @return
     */
    @Delete("delete from student where id = #{id}")
    Integer deleteById(@Param("id") Integer id);
}
