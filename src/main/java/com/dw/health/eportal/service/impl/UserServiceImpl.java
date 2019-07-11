package com.dw.health.eportal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dw.health.eportal.dao.DepartmentMapper;
import com.dw.health.eportal.dao.RelUserDeptMapper;
import com.dw.health.eportal.dao.RelUserRoleMapper;
import com.dw.health.eportal.dao.RoleMapper;
import com.dw.health.eportal.dao.UserMapper;
import com.dw.health.eportal.entity.RelUserDept;
import com.dw.health.eportal.entity.RelUserRole;
import com.dw.health.eportal.entity.Role;
import com.dw.health.eportal.entity.User;
import com.dw.health.eportal.entity.dto.UserDTO;
import com.dw.health.eportal.mo.UserConvertMapper;
import com.dw.health.eportal.service.OpenfireService;
import com.dw.health.eportal.service.UserService;
import com.dw.health.eportal.util.BaseEntityUtil;
import com.dw.health.eportal.util.CurrentUserUtil;
import com.dw.health.eportal.util.PinYinUtil;
import com.dw.health.framework.consts.GlobalNames;
import com.dw.health.framework.exception.BizException;
import com.github.pagehelper.PageHelper;


/**
 * @author gyq 
 * @version V1.0 
 * @ClassName: UserServiceImpl
 * @Description: 用户service接口实现
 * @date 2019-2-28 8:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RelUserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private DepartmentMapper deptMapper;
    @Autowired
    private RelUserDeptMapper userDeptMapper;
    @Autowired
    private OpenfireService openfireService;

    @Override
    public int insert(UserDTO userDTO) {
        User user = UserConvertMapper.userConvertMapper.toUser(userDTO);
        // 校验两次密码是否一致
        if (!user.getPassword().equals(userDTO.getConfirmPassword())){
            throw new BizException("两次密码不一致，请重试");
        }
        // 校验userName是否唯一
        int count = userMapper.selectCount(new User().setUserName(user.getUserName()));
        if (count > 0){
            throw new BizException("当前用户名已经存在");
        }
        // 生成盐值
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        user.setSalt(salt);
        // 生成用户密码
        String password = new Md5Hash(user.getPassword(), salt).toString();
        user.setPassword(password);
        // 设置是否锁定,默认不锁定
        user.setLocked("0");
        // 设置用户名首拼
        user.setSp(PinYinUtil.getFirstSpell(user.getRealName()));
        // 通用mapper的insert方法 会把生成的key设置到实体类中s
        int num = userMapper.insertSelective(BaseEntityUtil.insertEntity(user));
        // 为用户设置部门
        RelUserDept relUserDept = new RelUserDept().setUserId(user.getUserId()).setDeptId(userDTO.getDeptId());
        userDeptMapper.insertSelective(BaseEntityUtil.insertEntity(relUserDept));
        return num;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDTO selectByUserId(String userId) {
        UserDTO userDTO = userMapper.selectUserDTOById(userId);
        return userDTO;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserDTO> selectUsersByKeywords(Map<String, String> searchMap) {
        String keyword = searchMap.get("keyword");
        List<UserDTO> users = userMapper.selectUsersByKeyword(keyword);
        return users;
    }

    @Override
    public int updateByPrimaryKeySelective(User user) {
        return userMapper.updateByPrimaryKeySelective(BaseEntityUtil.updateEntity(user));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserDTO> selectBySearchDTO(UserDTO userDTO) {
        PageHelper.startPage(userDTO.getCurrentPage(), userDTO.getPageSize());
        List<UserDTO> users = userMapper.selectBySearchDTO(userDTO);
        return users;
    }

    @Override
    public void deleteUser(List<User> userList) {
        for (User user : userList) {
            userMapper.deleteByPrimaryKey(BaseEntityUtil.deleteEntity(user));
            userRoleMapper.deleteRelUserRole(BaseEntityUtil.deleteEntity(user));
            // 物理删除中间表信息
            userDeptMapper.deleteRelUserDept(user.getUserId());
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Role> selectRoleList(UserDTO userDTO) {
        PageHelper.startPage(userDTO.getCurrentPage(), userDTO.getPageSize());
        List<Role> roleList = roleMapper.selectRoleListByUserId(userDTO.getUserId());
        return roleList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Role> selectNRoleList(UserDTO userDTO) {
        PageHelper.startPage(userDTO.getCurrentPage(), userDTO.getPageSize());
        List<Role> roleList = roleMapper.selectNRoleListByUserId(userDTO.getUserId());
        return roleList;
    }

    @Override
    public void addRoleList(List<Role> rolelist, String userId) {
        for (Role role : rolelist) {
            RelUserRole relUserRole = new RelUserRole();
            relUserRole.setUserId(userId);
            relUserRole.setRoleId(role.getRoleId());
            userRoleMapper.insertSelective(BaseEntityUtil.insertEntity(relUserRole));
        }
    }

    @Override
    public void deleteRoleList(List<Role> rolelist, String userId) {
        for (Role role : rolelist) {
            RelUserRole relUserRole = new RelUserRole();
            relUserRole.setUserId(userId);
            relUserRole.setRoleId(role.getRoleId());
            userRoleMapper.logicDelete(BaseEntityUtil.deleteEntity(relUserRole));
        }
    }

    @Override
    public void unLock(User user) {
        User userLocked = userMapper.selectByPrimaryKey(user.getUserId());
        userLocked.setLocked("0");
        userMapper.updateByPrimaryKeySelective(BaseEntityUtil.updateEntity(userLocked));
    }

    @Override
    public void changePassword(UserDTO userDTO) {
        // 验证两次输入密码是否一致
        if (!userDTO.getNewPassword().equals(userDTO.getConfirmPassword())){
            throw new BizException("两次密码不一致，请重试");
        }
        User user = CurrentUserUtil.getCurrentUser();
        String salt = user.getSalt();
        String password = new Md5Hash(userDTO.getPassword(), salt).toString();
        // 验证原密码是否正确
        if (!password.equals(user.getPassword())){
            throw new BizException("原密码不正确，请重试");
        }
        // 验证原密码是否与新密码一致
        String newPassword = new Md5Hash(userDTO.getNewPassword(), salt).toString();
        if (password.equals(newPassword)){
            throw new BizException("原密码与新密码相同，请重试");
        }
        // 修改密码
        user.setPassword(newPassword);
        userMapper.updateByPrimaryKeySelective(BaseEntityUtil.updateEntity(user));
    }

    @Override
    public void resetPassword(User user) {
        // 密码统一重置为123
        String password = GlobalNames.DEFAULT_PASSWORD;
        User currentUser = userMapper.selectByPrimaryKey(user.getUserId());
        String newPassword = new Md5Hash(password, currentUser.getSalt()).toString();
        currentUser.setPassword(newPassword);
        userMapper.updateByPrimaryKeySelective(BaseEntityUtil.updateEntity(currentUser));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<String> listDepartment(String userId) {
        List<String> departments = deptMapper.listDeptsByUserId(userId);
        return departments;
    }
}
