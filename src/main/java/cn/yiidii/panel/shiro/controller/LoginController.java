package cn.yiidii.panel.shiro.controller;

import cn.yiidii.panel.annotation.OptLogAnnotation;
import cn.yiidii.panel.core.vo.ResponseStatusEnum;
import cn.yiidii.panel.core.vo.ResponseWarper;
import cn.yiidii.panel.ex.ServiceException;
import cn.yiidii.panel.shiro.entity.User;
import cn.yiidii.panel.shiro.service.impl.UserService;
import cn.yiidii.panel.shiro.util.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@RestController
@Validated
@Api(tags = "认证")
public class LoginController {

    @Autowired
    public UserService userService;

    @GetMapping("/un_auth")
    @ApiOperation(value = "未认证", notes = "shiro的LoginUrl，返回用户的登陆状态")
    public String unAnth() {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isEmpty(token)) {
            throw new ServiceException(ResponseStatusEnum.USER_UNAUTH);
        }
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        User user = userService.queryUserByUsername(username);
        if (Objects.isNull(user)) {
            return ResponseWarper.error(ResponseStatusEnum.TOKEN_AUTH_FAILED).toJSON();
        } else {
            return ResponseWarper.success("用户已登录").toJSON();
        }
    }

    @PostMapping(value = "/login")
    @OptLogAnnotation
    @ApiOperation(value = "用户登录", notes = "")
    public String login(@ApiParam("用户名") @NotBlank(message = "用户名不能为空") @RequestParam("username") String username,
                        @ApiParam("密码") @NotBlank(message = "密码不能为空") @RequestParam("password") String password) throws ServiceException {
        User user = userService.queryUserByUsername(username);
        if (StringUtils.equals(password, user.getPassword())) {
            JSONObject jo = new JSONObject();
            jo.put("username", username);
            jo.put("name", userService.queryUserByUsername(username).getName());
            jo.put("auth", JWTUtil.createToken(username));
            return ResponseWarper.success("登陆成功", jo).toJSON();
        } else {
            throw new ServiceException(ResponseStatusEnum.INCORRECT_USERNAME_OR_PASSWORD);
        }
    }

    @GetMapping("/logout")
    @ApiOperation(value = "用户注销", notes = "")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        System.out.println("un_auth user: " + (User) subject.getPrincipal());
        subject.logout();
        return ResponseWarper.success("用户注销成功").toJSON();
    }


    @PostMapping("/reg")
    @ApiOperation(value = "注册", notes = "")
    public String reg(@ApiParam("用户信息") @Validated User user) {
        if (1 == userService.reg(user)) {
            return ResponseWarper.success("系统已向注册邮箱【" + user.getEmail() + "】发送了一封激活邮件，请前往激活").toJSON();
        }
        return ResponseWarper.serviceError("注册发生异常，请联系系统管理员").toJSON();
    }

    @GetMapping("/activeAccount")
    @ApiOperation(value = "激活用户", notes = "注册的用户默认是没有激活的需要到注册邮箱激活，调用此接口")
    public String activeAccount(@RequestParam("username") String username, @RequestParam("code") String code) {
        if (userService.activeAccount(username, code)) {
            return ResponseWarper.success("用户" + username + "激活成功").toJSON();
        }
        return ResponseWarper.success("用户" + username + "激活失败，请联系系统管理员").toJSON();
    }

    @GetMapping("/401")
    @ApiOperation(value = "401，没有权限", notes = "没有权限默认都会跳转此接口，返回无权限json")
    public String unauthorized(HttpSession session) {
        return ResponseWarper.error(ResponseStatusEnum.NO_PERMISSION).toJSON();
    }

}