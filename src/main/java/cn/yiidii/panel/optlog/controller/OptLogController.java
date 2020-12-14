package cn.yiidii.panel.optlog.controller;

import cn.yiidii.panel.common.utils.PageUtil;
import cn.yiidii.panel.optlog.service.impl.OptLogService;
import cn.yiidii.panel.pojo.OptLog;
import cn.yiidii.panel.pojo.PageResult;
import cn.yiidii.panel.shiro.entity.User;
import cn.yiidii.panel.shiro.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Api(tags = "操作日志")
public class OptLogController {

    @Autowired
    public OptLogService optLogService;

    @GetMapping("/optlog")
    @ResponseBody
//    @OptLogAnnotation
    @ApiOperation(value = "查询操作日志")
    public String getOptLog(Integer page, Integer pageSize) {
        User user = SecurityUtil.getCurrUser();
        PageResult<OptLog> optLogs = null;
        if (Objects.isNull(user)) {
            optLogs = (PageResult<OptLog>) optLogService.queryLogWithoutUid(page, pageSize);
        } else {
            optLogs = (PageResult<OptLog>) optLogService.queryLogByUid(user.getId(), page, pageSize);
        }
        return PageUtil.parsePageResult(optLogs);
    }

}
