package com.dw.health.eportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dw.health.eportal.dao.PermissionMapper;
import com.dw.health.eportal.dao.RelRolePermissionMapper;
import com.dw.health.eportal.dao.RoleMapper;
import com.dw.health.eportal.entity.Permission;
import com.dw.health.eportal.entity.RelRolePermission;
import com.dw.health.eportal.entity.Role;
import com.dw.health.eportal.entity.dto.PermissionDTO;
import com.dw.health.eportal.service.PermissionService;
import com.dw.health.eportal.util.BaseEntityUtil;
import com.dw.health.framework.exception.BizException;
import com.github.pagehelper.PageHelper;

/**
 * 权限的增删改查
 * @Author: qjf
 * @Date: 2019/2/28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RelRolePermissionMapper rolePermissionMapper;

    //---------------------------- 权限相关 -------------------------
    @Override
    public void addPermission(Permission permission) {
        int count = permissionMapper.selectCount(new Permission().setPermFlag(permission.getPermFlag()));
        if (count > 0){
            throw new BizException("该权限已经存在");
        }
        permissionMapper.insertSelective(BaseEntityUtil.insertEntity(permission));
    }

    @Override
    public void deletePermission(List<Permission> permissionList) {
        for (Permission permission : permissionList) {
            permissionMapper.deleteByPrimaryKey(BaseEntityUtil.deleteEntity(permission));
            rolePermissionMapper.deleteRelPermissionRole(BaseEntityUtil.deleteEntity(permission));
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PermissionDTO selectByPermissionId(String permissionId) {
        PermissionDTO permDTO = permissionMapper.selectByPermId(permissionId);
        return permDTO;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<PermissionDTO> selectPermListBySearchDTO(PermissionDTO permissionDTO) {
        PageHelper.startPage(permissionDTO.getCurrentPage(), permissionDTO.getPageSize());
        List<PermissionDTO> list = permissionMapper.selectPermListBySearchDTO(permissionDTO);
        return list;
    }

    @Override
    public void updatePermission(Permission permission) {
        permissionMapper.updateByPrimaryKeySelective(BaseEntityUtil.updateEntity(permission));
    }


    //---------------------------- 角色相关 -------------------------

    @Override
    public void addRoleList(List<Role> roleList, String permId) {
        for (Role role : roleList) {
            RelRolePermission rolePermission = new RelRolePermission();
            rolePermission.setPermId(permId);
            rolePermission.setRoleId(role.getRoleId());
            rolePermissionMapper.insertSelective(BaseEntityUtil.insertEntity(rolePermission));
        }
    }

    @Override
    public void deleteRoleList(List<Role> roleList, String permId) {
        for (Role role : roleList) {
            RelRolePermission rolePermission = new RelRolePermission();
            rolePermission.setPermId(permId);
            rolePermission.setRoleId(role.getRoleId());
            rolePermissionMapper.logicDelete(BaseEntityUtil.deleteEntity(rolePermission));
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Role> selectRoleList(PermissionDTO permissionDTO) {
        PageHelper.startPage(permissionDTO.getCurrentPage(), permissionDTO.getPageSize());
        List<Role> roleList = roleMapper.selectRoleListByPermId(permissionDTO.getPermId());
        return roleList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Role> selectNRoleList(PermissionDTO permissionDTO) {
        PageHelper.startPage(permissionDTO.getCurrentPage(), permissionDTO.getPageSize());
        List<Role> roleList = roleMapper.selectNRoleListByPermId(permissionDTO.getPermId());
        return roleList;
    }

}
