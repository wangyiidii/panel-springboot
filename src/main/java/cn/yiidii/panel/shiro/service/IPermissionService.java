package cn.yiidii.panel.shiro.service;

import cn.yiidii.panel.shiro.entity.Permission;

import java.util.Set;

public interface IPermissionService {
    Set<Permission> queryPermissionByUsername(String username);
    Set<Permission> queryPermissionByRoleName(String roleName);
    Set<Permission> queryAllPermission();
}
