package com.zzl.newprojectspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzl.newprojectspring.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.stream.BaseStream;

public interface StudentMapper extends BaseMapper<Student> {

//    /**
//     * 查询所有用户
//     */
//    @Select("select * from student")
//    List<Student> findAll();
//
//    @Insert("insert into newProject.`student` (`id`, `studentNo`, `studentPassword`, `studentName`, `studentSex`, `studentCollege`, `studentMajor`, `studentGpa`) values " +
//            "(#{id}, #{studentNo}, #{studentPassword}, #{studentName}, #{studentSex}, #{studentCollege}, #{studentMajor}, #{studentGpa})")
//    int insert(Student student);
//
//    /**
//     * 更新或修改
//     *
//     * @param student
//     * @return
//     */
//    int update(Student student);
//
//    /**
//     * 删除
//     *
//     * @param id
//     * @return
//     */
//    @Delete("delete from student where id = #{id}")
//    Integer deleteById(@Param("id") Integer id);
//
//    /**
//     * 实现分页查询
//     *
//     * @param pageNum
//     * @param pageSize
//     */
//    @Select("select * from student where studentName like concat('%' ,#{studentName},'%') " +
//            "and studentNo like concat('%',#{studentNo},'%') " +
//            "and studentSex like concat('%',#{studentSex},'%') " +
//            "and studentMajor like concat('%',#{studentMajor},'%')" +
//            "limit #{pageNum}, #{pageSize}")
//    List<Student> selectPage(Integer pageNum, Integer pageSize, String studentName, String studentNo, String studentSex, String studentMajor);
//
//    @Select("select count(*) from student where studentName like concat('%' ,#{studentName},'%') " +
//            "and studentNo like concat('%',#{studentNo},'%') " +
//            "and studentSex like concat('%',#{studentSex},'%')" +
//            "and studentMajor like concat('%',#{studentMajor},'%')")
//    Integer selectTotal(String studentName, String studentNo, String studentSex, String studentMajor);
}
