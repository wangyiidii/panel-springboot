package cn.yiidii.panel.shiro.service;

import cn.yiidii.panel.shiro.entity.Role;

import java.util.Set;

public interface IRoleService {

    Set<Role> queryAllRole();
    Set<Role> queryRoleByUserName(String username);

}
