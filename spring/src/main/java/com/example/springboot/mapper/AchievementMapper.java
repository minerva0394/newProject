package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Achievement;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zengzl
 */
public interface AchievementMapper extends BaseMapper<Achievement> {


    Page<Achievement> findPage(Page<Achievement> page, @Param("stu_number") String stuNumber);
}
