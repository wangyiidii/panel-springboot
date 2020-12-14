package cn.yiidii.panel.openapi.controller;

import cn.yiidii.panel.annotation.OptLogAnnotation;
import cn.yiidii.panel.common.utils.ServerConfig;
import cn.yiidii.panel.common.utils.YiYan;
import cn.yiidii.panel.core.vo.ResponseWarper;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
@Api(tags ="开放API")
public class CommonController {

    @Autowired
    private ServerConfig serverConfig;

    @GetMapping("/yiyan")
    @ApiOperation(value = "一言接口")
    @OptLogAnnotation
    public String randomYiYan() {
        String[] yiyan = YiYan.randomYiYan();
        JSONObject jo = new JSONObject();
        jo.put("content",yiyan[0]);
        jo.put("from",yiyan[1]);
        return ResponseWarper.success(jo).toJSON();
    }
    @GetMapping("/addr")
    @OptLogAnnotation
    @ApiOperation(value = "当前服务器地址",notes = "http://ip:port/")
    public String address() {
        String url = serverConfig.getUrl();
        return ResponseWarper.success(url).toJSON();
    }
}
