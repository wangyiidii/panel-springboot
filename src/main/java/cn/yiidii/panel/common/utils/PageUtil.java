package cn.yiidii.panel.common.utils;

import cn.yiidii.panel.pojo.PageResult;
import com.alibaba.fastjson.JSONObject;

public class PageUtil {

    public static String parsePageResult(PageResult<?> obj) {
        JSONObject jo = new JSONObject();
        jo.put("total", obj.getTotal());
        //jo.put("pageCount", obj.getPageCount());
        jo.put("rows", obj.getList());
        //jo.put("page", obj.getCurrent());
        return jo.toJSONString();
    }

}
