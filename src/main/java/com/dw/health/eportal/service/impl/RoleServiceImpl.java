package com.dw.health.eportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dw.health.eportal.dao.PermissionMapper;
import com.dw.health.eportal.dao.RelRolePermissionMapper;
import com.dw.health.eportal.dao.RelUserRoleMapper;
import com.dw.health.eportal.dao.RoleMapper;
import com.dw.health.eportal.dao.UserMapper;
import com.dw.health.eportal.entity.Permission;
import com.dw.health.eportal.entity.RelRolePermission;
import com.dw.health.eportal.entity.RelUserRole;
import com.dw.health.eportal.entity.Role;
import com.dw.health.eportal.entity.User;
import com.dw.health.eportal.entity.dto.RoleDTO;
import com.dw.health.eportal.service.RoleService;
import com.dw.health.eportal.util.BaseEntityUtil;
import com.dw.health.framework.exception.BizException;
import com.github.pagehelper.PageHelper;

/**
 * 角色的增删改查
 * @Author: qjf
 * @Date: 2019/2/28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permMapper;
    @Autowired
    private RelUserRoleMapper userRoleMapper;
    @Autowired
    private RelRolePermissionMapper rolePermissionMapper;
    //---------------------------- 角色相关 -------------------------
    @Override
    public void addRole(Role role) {
        int count = roleMapper.selectCount(new Role().setRoleFlag(role.getRoleFlag()));
        if (count > 0){
            throw new BizException("该角色已经存在");
        }
        roleMapper.insertSelective(BaseEntityUtil.insertEntity(role));
    }

    @Override
    public void deleteRoleList(List<Role> roleList) {
        for (Role role : roleList) {
            // 逻辑删除该角色
            roleMapper.deleteByPrimaryKey(BaseEntityUtil.deleteEntity(role));
            // 逻辑删除角色与权限的关系
            rolePermissionMapper.deleteRelRolePermission(BaseEntityUtil.deleteEntity(role));
            // 逻辑删除角色与用户的关系
            userRoleMapper.deleteRelRoleUser(BaseEntityUtil.deleteEntity(role));
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public RoleDTO selectByRoleId(String roleId) {
        RoleDTO roleDTO = roleMapper.selectByRoleId(roleId);
        return roleDTO;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<RoleDTO> selectRoleListBySearchDTO(RoleDTO roleDTO) {
        PageHelper.startPage(roleDTO.getCurrentPage(), roleDTO.getPageSize());
        List<RoleDTO> list = roleMapper.selectRoleListBySearchDTO(roleDTO);
        return list;
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(BaseEntityUtil.updateEntity(role));
    }


    //---------------------------- 用户相关 -------------------------
    @Override
    public void addUserList(List<User> userList, String roleId) {
        for (User user : userList) {
            RelUserRole userRole = new RelUserRole().setRoleId(roleId).setUserId(user.getUserId());
            userRoleMapper.insertSelective(BaseEntityUtil.insertEntity(userRole));
        }
    }

    @Override
    public void deleteUserList(List<User> userList, String roleId) {
        for (User user : userList) {
            RelUserRole userRole = new RelUserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(user.getUserId());
            userRoleMapper.logicDelete(BaseEntityUtil.deleteEntity(userRole));
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> selectUserList(RoleDTO roleDTO) {
        PageHelper.startPage(roleDTO.getCurrentPage(), roleDTO.getPageSize());
        List<User> userList = userMapper.selectUserListByRoleId(roleDTO.getRoleId());
        return userList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> selectNUserList(RoleDTO roleDTO) {
        PageHelper.startPage(roleDTO.getCurrentPage(), roleDTO.getPageSize());
        List<User> userList = userMapper.selectNUserListByRoleId(roleDTO.getRoleId());
        return userList;
    }


    //---------------------------- 权限相关 -------------------------
    @Override
    public void addPermList(List<Permission> permList, String roleId) {
        for (Permission perm : permList) {
            RelRolePermission rolePermission = new RelRolePermission();
            rolePermission.setPermId(perm.getPermId());
            rolePermission.setRoleId(roleId);
            rolePermissionMapper.insertSelective(BaseEntityUtil.insertEntity(rolePermission));
        }
    }

    @Override
    public void deletePermList(List<Permission> permList, String roleId) {
        for (Permission perm : permList) {
            RelRolePermission rolePermission = new RelRolePermission();
            rolePermission.setPermId(perm.getPermId());
            rolePermission.setRoleId(roleId);
            rolePermissionMapper.logicDelete(BaseEntityUtil.deleteEntity(rolePermission));
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Permission> selectPermList(RoleDTO roleDTO) {
        PageHelper.startPage(roleDTO.getCurrentPage(), roleDTO.getPageSize());
        List<Permission> permList = permMapper.selectPermListByRoleId(roleDTO.getRoleId());
        return permList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Permission> selectNPermList(RoleDTO roleDTO) {
        PageHelper.startPage(roleDTO.getCurrentPage(), roleDTO.getPageSize());
        List<Permission> permList = permMapper.selectNPermListByRoleId(roleDTO.getRoleId());
        return permList;
    }

}
