package com.dw.health.eportal.manager;

import com.dw.health.core.security.shiro.UserRealmManager;
import com.dw.health.eportal.dao.PermissionMapper;
import com.dw.health.eportal.dao.RoleMapper;
import com.dw.health.eportal.dao.UserMapper;
import com.dw.health.eportal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 获取用户相关信息
 * @Author: qjf
 * @Date: 2019/2/25
 */
@Repository
public class UserRealmManagerImpl implements UserRealmManager {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public User getUser(String userName) {
        User user = userMapper.selectOne(new User().setUserName(userName));
        return user;
    }
    @Override
    public List<String> getRoles(String userId) {
        List<String> roles = roleMapper.selectByUserId(userId);
        return roles;
    }

    @Override
    public List<String> getPermissions(String userId) {
        List<String> perms = permissionMapper.selectByUserId(userId);
        return perms;
    }
}
