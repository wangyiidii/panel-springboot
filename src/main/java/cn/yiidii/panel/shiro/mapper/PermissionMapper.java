package cn.yiidii.panel.shiro.mapper;

import cn.yiidii.panel.shiro.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface PermissionMapper {
    Set<Permission> queryAllPermission();

    Set<Permission> queryPermissionByUsername(String username);

    Set<Permission> queryPermissionByRoleName(String roleName);

//    List<Permission> queryPermissionByRoleName(String roleName);

//    Integer insert(Permission permission);
//
//    Integer update(Permission permission);
//
//    Integer del(Integer id);
}
