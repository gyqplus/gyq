package com.dw.health.eportal.dao;

import com.dw.health.framework.mybatis.BaseMapper;
import com.dw.health.eportal.entity.Permission;
import com.dw.health.eportal.entity.dto.PermissionDTO;
import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission>{
    List<String> selectByUserId(String userId);

    PermissionDTO selectByPermId(String permissionId);

    List<PermissionDTO> selectPermListBySearchDTO(PermissionDTO permissionDTO);

    List<Permission> selectPermListByRoleId(String roleId);

    List<Permission> selectNPermListByRoleId(String roleId);
}