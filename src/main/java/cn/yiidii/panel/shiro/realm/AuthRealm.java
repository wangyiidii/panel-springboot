package cn.yiidii.panel.shiro.realm;

import cn.yiidii.panel.shiro.entity.Permission;
import cn.yiidii.panel.shiro.entity.Role;
import cn.yiidii.panel.shiro.entity.User;
import cn.yiidii.panel.shiro.jwt.JWTToken;
import cn.yiidii.panel.shiro.service.impl.PermissionService;
import cn.yiidii.panel.shiro.service.impl.RoleService;
import cn.yiidii.panel.shiro.service.impl.UserService;
import cn.yiidii.panel.shiro.util.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        User user = userService.queryUserByUsername(username);
        if (user == null || !JWTUtil.verify(token, username)) {
            throw new AuthenticationException("token认证失败！");
        }
        if (!user.isState()) {
            throw new AuthenticationException("用户状态异常");
        }
        JWTToken jwtToken = new JWTToken(token);
        SecurityUtils.getSubject().login(jwtToken);
        return new SimpleAuthenticationInfo(token, token, getName());
    }

    /**
     * 权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("————权限认证————");
        String username = JWTUtil.getUsername(principals.toString());
        User user = userService.queryUserByUsername(username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 给该用户设置角色
        for (Role role : roleService.queryRoleByUserName(user.getUsername())) {
            authorizationInfo.addRole(role.getName());
        }
        // 给该用户设置权限，权限信息存在t_permission表中取
        for (Permission permission : permissionService.queryPermissionByUsername(user.getUsername())) {
            authorizationInfo.addStringPermission(permission.getPermission());
        }
        return info;
    }
}
