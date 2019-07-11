package com.dw.health.eportal.dao;

import com.dw.health.framework.mybatis.BaseMapper;
import com.dw.health.eportal.entity.Permission;
import com.dw.health.eportal.entity.RelRolePermission;
import com.dw.health.eportal.entity.Role;

public interface RelRolePermissionMapper extends BaseMapper<RelRolePermission> {
    int deleteRelRolePermission(Role role);

    int deleteRelPermissionRole(Permission permission);

    void logicDelete(RelRolePermission relRolePermission);
}