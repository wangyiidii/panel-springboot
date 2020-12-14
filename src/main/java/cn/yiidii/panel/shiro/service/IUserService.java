package cn.yiidii.panel.shiro.service;

import cn.yiidii.panel.shiro.entity.User;

import java.util.List;

public interface IUserService {
    List<User> queryAllUser();

    User queryUserById(Integer id);

    User queryUserByUsername(String username);

    Integer reg(User user);

    Integer update(User user);

    Integer del(Integer id);

    boolean activeAccount(String username, String code);
}
