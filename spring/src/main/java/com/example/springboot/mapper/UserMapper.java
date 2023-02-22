package com.example.springboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zengzl
 */
public interface UserMapper extends BaseMapper<User> {

    Page<User> findPage(Page<User> page, @Param("username") String username, @Param("email") String email, @Param("address") String address);

}
