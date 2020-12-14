package cn.yiidii.panel.shiro.controller;

import cn.yiidii.panel.shiro.entity.Role;
import cn.yiidii.panel.shiro.service.impl.RoleService;
import cn.yiidii.panel.shiro.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SecurityHelper {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    public boolean isContainsRole(String username) {
        Set<Role> roleSet = roleService.queryRoleByUserName(username);
        for (Role role : roleSet) {
            if (role.getName().equals("admin")) {
                return true;
            }
        }
        return false;
    }

    public boolean isAdminRole(String roleName) {
        if (null != roleName && roleName.equals("admin")) {
            return true;
        }
        return false;
    }
}
