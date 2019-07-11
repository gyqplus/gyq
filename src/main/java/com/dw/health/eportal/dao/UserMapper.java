package com.dw.health.eportal.dao;

import com.dw.health.framework.mybatis.BaseMapper;
import com.dw.health.eportal.entity.User;
import com.dw.health.eportal.entity.dto.UserDTO;
import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    UserDTO selectUserDTOById(String userId);

    List<UserDTO> selectUsersByKeyword(String keyword);

    List<UserDTO> selectBySearchDTO(UserDTO userDTO);

    List<User> selectUserListByRoleId(String roleId);

    List<User> selectNUserListByRoleId(String roleId);
}