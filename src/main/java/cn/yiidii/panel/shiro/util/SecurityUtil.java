package cn.yiidii.panel.shiro.util;

import cn.yiidii.panel.shiro.entity.User;
import cn.yiidii.panel.shiro.service.impl.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class SecurityUtil {

    @Autowired
    private static UserService userService;

    public static User getCurrUser() {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        return userService.queryUserByUsername(username);
    }

}
