package cn.yiidii.panel.shiro.service.impl;

import cn.yiidii.panel.shiro.mapper.PermissionMapper;
import cn.yiidii.panel.shiro.entity.Permission;
import cn.yiidii.panel.shiro.service.IShiroService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

@Service
public class ShiroService implements IShiroService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Map<String, String> loadFilterChainDefinitionMap() {
        // 权限控制map
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/index.jsp", "anon");
        filterChainDefinitionMap.put("/third/bjBus/**", "anon");
        filterChainDefinitionMap.put("/optlog/**", "anon");
        filterChainDefinitionMap.put("/common/**", "anon");
        filterChainDefinitionMap.put("/un_auth", "jwt");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/reg", "anon");
        filterChainDefinitionMap.put("/activeAccount", "anon");
        filterChainDefinitionMap.put("/ws/**", "anon");
        filterChainDefinitionMap.put("/job/**", "anon");
        filterChainDefinitionMap.put("/loveBook/**", "anon");

        //swagger2
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources", "anon");
        filterChainDefinitionMap.put("/swagger-resources/configuration/security", "anon");
        filterChainDefinitionMap.put("/swagger-resources/configuration/ui", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/webjars/springfox-swagger-ui/**", "anon");

        //filterChainDefinitionMap.put("/**", "roles[\"admin\"]");
        filterChainDefinitionMap.put("/**", "jwt");

        Set<Permission> permissionSet = permissionMapper.queryAllPermission();
        if (!CollectionUtils.isEmpty(permissionSet)) {
            permissionSet.forEach(e -> {
                StringJoiner sj = new StringJoiner(",", "perms[\"", "\"]");
                filterChainDefinitionMap.put(e.getUrl(), sj.add(e.getPermission()).toString());
            });
        }

        filterChainDefinitionMap.put("/**", "authc");
        return filterChainDefinitionMap;
    }

    @Override
    public void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean, Integer roleId, Boolean isRemoveSession) {

    }

    @Override
    public void updatePermissionByRoleId(Integer roleId, Boolean isRemoveSession) {

    }
}
