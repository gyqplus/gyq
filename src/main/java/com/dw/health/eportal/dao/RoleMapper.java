package com.dw.health.eportal.dao;

import com.dw.health.framework.mybatis.BaseMapper;
import com.dw.health.eportal.entity.Role;
import com.dw.health.eportal.entity.dto.RoleDTO;
import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    RoleDTO selectByRoleId(String roleId);

    List<String> selectByUserId(String userId);

    List<RoleDTO> selectRoleListBySearchDTO(RoleDTO roleDTO);

    /**
     * 查询用户拥有的角色列表
     * @param userId
     * @return
     */
    List<Role> selectRoleListByUserId(String userId);

    /**
     * 查询未拥有的角色
     * @param userId
     * @return
     */
    List<Role> selectNRoleListByUserId(String userId);

    List<Role> selectRoleListByPermId(String permId);

    List<Role> selectNRoleListByPermId(String permId);
}