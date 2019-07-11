package com.dw.health.eportal.mo;

import com.dw.health.eportal.entity.User;
import com.dw.health.eportal.entity.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
 * 用于user与userDTO之间相互转换
 * @Author: qjf
 * @Date: 2019/4/13
 */
@Mapper
public interface UserConvertMapper {
    UserConvertMapper userConvertMapper = Mappers.getMapper(UserConvertMapper.class);
    @Mappings({})
    UserDTO toUserDTO(User user);
    List<UserDTO> toUserDTOList(List<User> userList);
    @Mappings({})
    User toUser(UserDTO userDTO);
    List<User> toUserList(List<UserDTO> userDTOList);
}
