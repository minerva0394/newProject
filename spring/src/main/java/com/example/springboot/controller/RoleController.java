package com.example.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Role;
import com.example.springboot.service.IRoleService;
import io.swagger.models.auth.In;
import org.apache.commons.collections4.Get;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zengzl
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Role role) {
        roleService.saveOrUpdate(role);
        return Result.success();
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable Integer id) {
        roleService.removeById(id);
        return Result.success();
    }

    @RequestMapping(path = "/del/batch", method = RequestMethod.POST)
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        roleService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(roleService.list());
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roleService.getById(id));
    }

    @RequestMapping(path = "/page", method = RequestMethod.GET)
    public Result findPage(@RequestParam String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        queryWrapper.orderByDesc("id");
        Object data = roleService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(data);
    }

    /**
     * 绑定角色菜单关系
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds) {
        roleService.setRoleMenu(roleId, menuIds);

        return Result.success();
    }

    @GetMapping("/roleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId) {

        return Result.success(roleService.getRoleMenu(roleId));
    }

}

