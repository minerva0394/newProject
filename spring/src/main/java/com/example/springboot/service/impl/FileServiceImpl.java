package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Files;
import com.example.springboot.entity.Menu;
import com.example.springboot.entity.Role;
import com.example.springboot.entity.RoleMenu;
import com.example.springboot.mapper.FileMapper;
import com.example.springboot.mapper.RoleMapper;
import com.example.springboot.mapper.RoleMenuMapper;
import com.example.springboot.service.IFileService;
import com.example.springboot.service.IMenuService;
import com.example.springboot.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements IFileService {


}
