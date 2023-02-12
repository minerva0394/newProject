package com.zzl.newprojectspring.controller;

import com.zzl.newprojectspring.StudentService.StudentService;
import com.zzl.newprojectspring.entity.Student;
import com.zzl.newprojectspring.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
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
     *
     * @return
     */
    @GetMapping
    public List<Student> index() {
        List<Student> all = studentMapper.findAll();
        return all;
    }

    /**
     * 实现保存和新增
     *
     * @param student
     * @return
     */
    @PostMapping
    public Integer save(@RequestBody Student student) {
        return studentService.save(student);
    }

    /**
     * 删除id
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id) {
        return studentMapper.deleteById(id);
    }

    /**
     * 实现分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam String studentName,
                                        @RequestParam String studentNo,
                                        @RequestParam String studentSex,
                                        @RequestParam String studentMajor) {
        pageNum = (--pageNum) * pageSize;
        List<Student> data = studentMapper.selectPage(pageNum, pageSize, studentName, studentNo, studentSex, studentMajor);
        Integer total = studentMapper.selectTotal(studentName, studentNo, studentSex, studentMajor);
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;
    }
}
