package cn.yiidii.panel.bjbus.controller;

import cn.yiidii.panel.annotation.OptLogAnnotation;
import cn.yiidii.panel.bjbus.BJBusUtil;
import cn.yiidii.panel.core.vo.ResponseWarper;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/third/bjBus")
@Api(tags = "北京公交")
public class BjbusController {

    @GetMapping(value = "realTime")
    @OptLogAnnotation(desc = "查询北京实时公交")
    @ApiOperation(value = "查询北京实时公交")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "bid", value = "公交线路ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "rid", value = "公交方向ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "sid", value = "公交站点ID", required = true)})
    public String getRealTimeBus(@RequestParam(name = "bid", required = true) String bid,
                                 @RequestParam(name = "rid", required = true) String rid,
                                 @RequestParam(name = "sid", required = true) String sid)
            throws Exception {
        return ResponseWarper.success(bid + "路公交查询实时公交成功", BJBusUtil.getRealTimeBus(bid, rid, sid)).toJSON();
    }

    @GetMapping(value = "bus")
    @ApiOperation(value = "北京公交线路列表")
    public String getBuses() throws Exception {
        return ResponseWarper.success(JSONObject.parseArray(JSONObject.toJSONString(BJBusUtil.getBuses()))).toJSON();
    }

    @GetMapping(value = "row")
    @ApiOperation(value = "北京公交方向列表", notes = "需要指定公交线路")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "bid", value = "公交线路ID", required = true),
    })
    public String getRows(@RequestParam(name = "bid", required = true) String bid) throws Exception {
        return ResponseWarper.success(JSONObject.parseArray(JSONObject.toJSONString(BJBusUtil.getRows(bid)))).toJSON();
    }

    @GetMapping(value = "station")
    @ApiOperation(value = "北京公交站点列表", notes = "需要指定公交线路和方向")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "bid", value = "公交线路ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "sid", value = "公交站点ID", required = true)})
    public String getStations(@RequestParam(name = "bid", required = true) String bid,
                              @RequestParam(name = "rid", required = true) String rid) throws Exception {
        return ResponseWarper.success(JSONObject.parseArray(JSONObject.toJSONString(BJBusUtil.getStations(bid, rid)))).toJSON();
    }

}
