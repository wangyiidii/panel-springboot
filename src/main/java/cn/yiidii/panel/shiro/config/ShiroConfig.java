package cn.yiidii.panel.shiro.config;

import cn.yiidii.panel.shiro.filter.JWTFilter;
import cn.yiidii.panel.shiro.service.impl.PermissionService;
import cn.yiidii.panel.shiro.service.impl.ShiroService;
import cn.yiidii.panel.shiro.realm.AuthRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    @Autowired
    PermissionService permissionService;

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     */
    @Bean(name = "shirFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager, ShiroService shiroService) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //过滤器
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //filtersMap.put("perms", new MyAuthorizationFilter());
        filtersMap.put("jwt", new JWTFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        shiroFilterFactoryBean.setLoginUrl("/un_auth");
        //shiroFilterFactoryBean.setSuccessUrl("/un_auth");
        shiroFilterFactoryBean.setUnauthorizedUrl("/401");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroService.loadFilterChainDefinitionMap());

        return shiroFilterFactoryBean;
    }

    /**
     * 配置核心安全事务管理器
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(AuthRealm());
        return securityManager;
    }

    /**
     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
     *
     * @return
     */
    @Bean
    public AuthRealm AuthRealm() {
        AuthRealm shiroRealm = new AuthRealm();
        //配置自定义密码比较器
        // shiroRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher());
        return shiroRealm;
    }

}
