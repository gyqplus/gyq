package com.dw.health.core.security.shiro;

import com.dw.health.eportal.entity.Permission;
import com.dw.health.eportal.entity.Role;
import com.dw.health.eportal.entity.User;

import java.util.List;

/**
 * 查询数据库中User信息
 * @Author: qjf
 * @Date: 2019/2/25
 */
public interface UserRealmManager {
    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return User
     */
    User getUser(String userName);

    /**
     * 根据userId查询该用户拥有的角色
     * @param userId
     * @return List<Role>：角色列表
     */
    List<String> getRoles(String userId);

    /**
     * 根据userId查询该用户拥有的权限
     * @param userId
     * @return List<Permission>：权限列表
     */
    List<String> getPermissions(String userId);
}
