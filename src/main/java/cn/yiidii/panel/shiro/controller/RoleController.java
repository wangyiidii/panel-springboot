package cn.yiidii.panel.shiro.controller;

import cn.yiidii.panel.shiro.service.impl.PermissionService;
import cn.yiidii.panel.shiro.service.impl.RoleService;
import cn.yiidii.panel.core.vo.ResponseWarper;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/role")
@Validated
@Api(tags = "角色")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/all")
    @ApiOperation(value = "获取所有角色列表")
    public String getAllRole() {
        return ResponseWarper.success(JSONObject.toJSON(roleService.queryAllRole())).toJSON();
    }

    @GetMapping("/permission/{roleName}")
    @ApiOperation(value = "获取角色的权限")
    public String getPermsByRoleName(@NotBlank(message = "角色名不能为空") @PathVariable("roleName") String roleName) {
        return ResponseWarper.success(JSONObject.toJSON(permissionService.queryPermissionByRoleName(roleName))).toJSON();
    }

}
