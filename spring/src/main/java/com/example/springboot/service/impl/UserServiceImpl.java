package com.example.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.UserDto;
import com.example.springboot.entity.Menu;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.RoleMapper;
import com.example.springboot.mapper.RoleMenuMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IMenuService;
import com.example.springboot.service.IUserService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zengzl
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    public Boolean saveUser(User user) {
        return saveOrUpdate(user);
    }

    @Override
    public Page<User> findPage(Page<User> page, String username, String email, String college) {
        return userMapper.findPage(page, username, email, college);

    }


    @Override
    public UserDto login(UserDto userDto) {
        User one = getUserInfo(userDto);
        if (one != null) {
            BeanUtil.copyProperties(one, userDto, true);
            // 设置登录token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDto.setToken(token);

            String role = one.getRole();
            List<Menu> rolesMenus = getRoleMenus(role);
            userDto.setMenus(rolesMenus);
            return userDto;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名密码错误");
        }
    }

    @Override
    public User register(UserDto userDto) {
        User one = getUserInfo(userDto);
        if (one == null) {
            one = new User();
            BeanUtil.copyProperties(userDto, one, true);
            save(one);  //写入数据库
        } else {
            throw new ServiceException(Constants.CODE_601, "用户名已存在");
        }
        return one;

    }

    private User getUserInfo(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDto.getUsername());
        queryWrapper.eq("password", userDto.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

    /**
     * 获取当前角色菜单列表及其子列表
     * @param role
     * @return
     */
    private List<Menu> getRoleMenus(String role){
        Integer roleId = roleMapper.selectByFlag(role);
        // 当前角色的所有菜单id集合
        List<Integer> meunIds = roleMenuMapper.selectByRoleId(roleId);

        // 查出系统所有的菜单
        List<Menu> menus = menuService.findMenus("");
        // 筛选完成的list
        List<Menu> rolesMenus = new ArrayList<>();
        // 筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if (meunIds.contains(menu.getId())) {
                rolesMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            children.removeIf(child -> !meunIds.contains(child.getId()));
        }
        return rolesMenus;
    }
}
