package com.example.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IUserService userService;

    @GetMapping("/example")
    public Result get() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return Result.success(map);
    }


    @GetMapping("/haveGPA")
    public Result members() {
        List<User> list = userService.list();
        // todo 改为已有绩点记录 ，q1对应第一学期
        double q1 = 0;
        double q2 = 0;
        double q3 = 0;
        double q4 = 0;
        double q5 = 0;
        double q6 = 0;
        double q7 = 0;
        double q8 = 0;
        for (User user : list) {
            q1 = user.getGpa1st();
            q2 = user.getGpa2nd();
            q3 = user.getGpa3rd();
            q4 = user.getGpa4th();
            q5 = user.getGpa5th();
            q6 = user.getGpa6th();
            q7 = user.getGpa7th();
            q8 = user.getGpa8th();
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4, q5, q6, q7, q8));
    }
}
