package cn.yiidii.panel.shiro.service.impl;

import cn.hutool.extra.mail.MailUtil;
import cn.yiidii.panel.common.utils.ServerConfig;
import cn.yiidii.panel.core.vo.ResponseStatusEnum;
import cn.yiidii.panel.ex.ServiceException;
import cn.yiidii.panel.shiro.entity.User;
import cn.yiidii.panel.shiro.mapper.UserMapper;
import cn.yiidii.panel.shiro.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ServerConfig serverConfig;

    @Override
    public List<User> queryAllUser() throws ServiceException {
        return userMapper.queryAllUser();
    }

    @Override
    public User queryUserById(Integer id) {
        return userMapper.queryUserById(id);
    }

    @Override
    public User queryUserByUsername(String username) throws ServiceException {
        return getUserByUsername(username);
    }

    @Override
    public Integer reg(User user) throws ServiceException {
        User exist = userMapper.queryUserByUsername(user.getUsername());
        if (null != exist) {
            throw new ServiceException(ResponseStatusEnum.USERNAME_ALREADY_EXISTS);
        }
        user.setEmailCode(UUID.randomUUID().toString());
        Integer row = userMapper.insert(user);
        if (row != 1) {
            throw new ServiceException("添加用户时发生异常");
        }
        MailUtil.send(user.getEmail(), user.getUsername() + " - 激活邮件", "<a href=\"" + serverConfig.getUrl() + "/activeAccount?username=" + user.getUsername() + "&code=" + user.getEmailCode() + "\">点我激活" + user.getUsername() + "</a>", true);
        return row;
    }

    @Override
    public Integer update(User user) {
        return userMapper.update(user);
    }

    @Override
    public Integer del(Integer id) {
        return userMapper.del(id);
    }

    @Override
    public boolean activeAccount(String username, String code) throws ServiceException {
        User exist = getUserByUsername(username);
        if (exist.isState()) {
            throw new ServiceException(ResponseStatusEnum.USER_ALREADY_ACTIVED);
        }
        if (StringUtils.equals(exist.getEmailCode(), code)) {
            exist.setState(true);
            userMapper.update(exist);
            return true;
        }
        return false;
    }

    private User getUserByUsername(String username) throws ServiceException {
        return userMapper.queryUserByUsername(username);
    }
}
