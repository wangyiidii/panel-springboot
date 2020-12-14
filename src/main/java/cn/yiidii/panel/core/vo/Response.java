package cn.yiidii.panel.core.vo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description= "返回响应数据")
public class Response<T> implements Serializable {
    @ApiModelProperty(value = "自定义状态码")
    private int code;
    @ApiModelProperty(value = "是否成功")
    private boolean success;
    @ApiModelProperty(value = "信息")
    private String message;
    @ApiModelProperty(value = "响应数据")
    private T data;
    @ApiModelProperty(value = "时间戳")
    private long timestamp;


    public Response(ResponseStatusEnum httpStatus, T data) {
        this.code = httpStatus.getCode();
        this.success = true;
        this.message = httpStatus.getMsg();
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public Response(int code, String msg, T data) {
        this.code = code;
        this.success = true;
        this.message = msg;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public Response(int code, String msg, boolean success) {
        this.code = code;
        this.success = success;
        this.message = msg;
        this.timestamp = System.currentTimeMillis();
    }

    public String toJSON() {
        return JSONObject.toJSONString(this);
    }
}
