package com.dw.health.eportal.dao;

import com.dw.health.framework.mybatis.BaseMapper;
import com.dw.health.eportal.entity.RelUserRole;
import com.dw.health.eportal.entity.Role;
import com.dw.health.eportal.entity.User;

public interface RelUserRoleMapper extends BaseMapper<RelUserRole> {
    /**
     * 逻辑删除用户下的角色,设置操作员id和删除时间
     * @author
     * @param user
     * @return
     */
    int deleteRelUserRole(User user);

    void logicDelete(RelUserRole relUserRole);

    void deleteRelRoleUser(Role role);
}