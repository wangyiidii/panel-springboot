package cn.yiidii.panel.shiro.mapper;

import cn.yiidii.panel.shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> queryAllUser();

    User queryUserById(Integer id);

    User queryUserByUsername(String username);

    Integer insert(User user);

    Integer update(User user);

    Integer del(Integer id);

}
