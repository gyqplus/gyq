
package com.dw.health.eportal.service;

import com.dw.health.eportal.entity.Department;
import com.dw.health.eportal.entity.Role;
import com.dw.health.eportal.entity.User;
import com.dw.health.eportal.entity.dto.UserDTO;
import java.util.List;
import java.util.Map;

/**
 * @author gyq 
 * @version V1.0 
 * @ClassName: UserService
 * @Description: 用户service接口
 * @date 2019-2-28 8:48
 */
public interface UserService {

    /**
     * 新增用户
     * @param userDTO
     * @return
     * @author qjf
     */
    int insert(UserDTO userDTO);

    UserDTO selectByUserId(String userId);

    List<UserDTO> selectUsersByKeywords(Map<String, String> searchMap);

    int updateByPrimaryKeySelective(User record);

    /**
     * 查询所有符合条件的用户
     *
     * @param userDTO
     * @return
     * @author qjf
     */
    List<UserDTO> selectBySearchDTO(UserDTO userDTO);

    void deleteUser(List<User> userList);

    List<Role> selectRoleList(UserDTO userDTO);

    List<Role> selectNRoleList(UserDTO userDTO);

    void addRoleList(List<Role> rolelist, String userId);

    void deleteRoleList(List<Role> rolelist, String userId);

    /**
     * 解锁
     * @param user
     */
    void unLock(User user);

    void changePassword(UserDTO userDTO);

    void resetPassword(User user);

    List<String> listDepartment(String userId);
}
