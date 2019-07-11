package com.dw.health.eportal.service;

import com.dw.health.eportal.entity.Permission;
import com.dw.health.eportal.entity.Role;
import com.dw.health.eportal.entity.dto.PermissionDTO;
import java.util.List;

/**
 * 实现权限的增删改查
 * @Author: qjf
 * @Date: 2019/2/28
 */
public interface PermissionService {
    /**
     * 新增权限
     * @param permission
     */
    void addPermission(Permission permission);

    /**
     * 删除权限
     * @param permissionList
     */
    void deletePermission(List<Permission> permissionList);

    /**
     * 根据id查询权限
     * @param permissionId
     * @return RoleDTO 组合权限相关信息
     */
    PermissionDTO selectByPermissionId(String permissionId);

    /**
     * 查询符合条件的权限信息
     * @param permissionDTO
     * @return
     */
    List<PermissionDTO> selectPermListBySearchDTO(PermissionDTO permissionDTO);

    /**
     * 修改角色信息
     * @param permission
     */
    void updatePermission (Permission permission);

    void addRoleList(List<Role> roleList, String permId);

    void deleteRoleList(List<Role> roleList, String permId);

    List<Role> selectRoleList(PermissionDTO permSearchDTO);

    List<Role> selectNRoleList(PermissionDTO permSearchDTO);
}
