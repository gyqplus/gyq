package com.dw.health.core.security.shiro;

import com.dw.health.eportal.dao.UserMapper;
import com.dw.health.eportal.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
/**
 * 用于认证和授权
 * @Author: qjf
 * @Date: 2019/2/27
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserRealmManager shiroDao;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());
        User user = shiroDao.getUser(username);
        if (user == null) {
            throw new AuthenticationException("用户名或密码错误");
        }
        String salt = user.getSalt();
        password = new Md5Hash(password, salt).toString();
        if (!password.equals(user.getPassword())) {
            throw new AuthenticationException("用户名或密码错误");
        }
        if ("1".equals(user.getLocked())){
            throw new LockedAccountException("当前用户被锁定");
        }
        if (user.getDeleteFlag()){
            throw new AuthenticationException("当前用户不存在");
        }
        SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(user.getUserId(), String.valueOf(token.getPassword()), getName());
        return authInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userId = (String) getAvailablePrincipal(principals);
        List<String> roles = shiroDao.getRoles(userId);
        List<String> permissions = shiroDao.getPermissions(userId);
        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
        authInfo.addRoles(roles);
        authInfo.addStringPermissions(permissions);
        return authInfo;
    }

    /**
     * 清空当前用户权限信息
     */
    public void clearCachedAuthorization() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
