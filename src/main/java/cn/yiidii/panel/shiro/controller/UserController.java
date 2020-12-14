package cn.yiidii.panel.shiro.controller;

import cn.yiidii.panel.core.vo.ResponseWarper;
import cn.yiidii.panel.shiro.entity.User;
import cn.yiidii.panel.shiro.service.impl.PermissionService;
import cn.yiidii.panel.shiro.service.impl.UserService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
@Api(tags ="用户")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public PermissionService permissionService;

    @GetMapping("/all")
    @ApiOperation(value = "获取所有用户")
    public String getUser() {
        List<User> users = userService.queryAllUser();
        JSONArray ja = (JSONArray) JSONObject.toJSON(users);
        for (Object o : ja) {
            JSONObject jo = (JSONObject) o;
            jo.remove("password");
        }

        return ResponseWarper.success(ja).toJSON();
    }

    @GetMapping("/info")
    @ApiOperation(value = "获取当前用户的基本信息")
    public String info() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        JSONObject jo = (JSONObject) JSONObject.toJSON(user);
        return ResponseWarper.success(jo).toJSON();
    }

    @GetMapping("/{username}")
    @ApiOperation(value = "根据用户名获取用户信息")
    public String getUser(@NotBlank(message = "参数用户名不能为空") @PathVariable("username") String username) {
        User user = userService.queryUserByUsername(username);
        if (null != user) {
            JSONObject userJo = (JSONObject) JSONObject.toJSON(user);
            userJo.remove("password");
            return ResponseWarper.success(userJo).toJSON();
        }
        return ResponseWarper.error().toJSON();
    }

    @GetMapping("/permission/{username}")
    @ApiOperation(value = "获取当前用户的权限")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "username", value = "用户名", required = true)})
    public String getPermsByUsername(@NotBlank(message = "参数用户名不能为空") @PathVariable("username") String username) {
        return ResponseWarper.success(JSONObject.toJSON(permissionService.queryPermissionByUsername(username))).toJSON();
    }




}
