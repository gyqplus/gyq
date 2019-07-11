package com.dw.health.eportal.service;

import com.dw.health.eportal.entity.Permission;
import com.dw.health.eportal.entity.Role;
import com.dw.health.eportal.entity.User;
import com.dw.health.eportal.entity.dto.RoleDTO;
import java.util.List;

/**
 * 角色的增删改查
 * @Author: qjf
 * @Date: 2019/2/28
 */
public interface RoleService {
    /**
     * 新增角色
     * @param role
     */
    void addRole(Role role);

    /**
     * 删除角色
     * @param roleList
     */
    void deleteRoleList(List<Role> roleList);

    /**
     * 根据id查询角色
     * @param roleId
     * @return RoleDTO 组合角色相关信息
     */
    RoleDTO selectByRoleId(String roleId);

    /**
     * 查询符合条件的角色信息
     * @param roleDTO
     * @return
     */
    List<RoleDTO> selectRoleListBySearchDTO(RoleDTO roleDTO);

    /**
     * 修改角色信息
     * @param role
     */
    void updateRole (Role role);

    void addUserList(List<User> userList, String roleId);

    void deleteUserList(List<User> userList, String roleId);

    List<User> selectUserList(RoleDTO roleDTO);

    List<User> selectNUserList(RoleDTO roleDTO);

    void addPermList(List<Permission> permList, String roleId);

    void deletePermList(List<Permission> permList, String roleId);

    List<Permission> selectPermList(RoleDTO roleDTO);

    List<Permission> selectNPermList(RoleDTO roleDTO);
}
