package cn.yiidii.panel.ex;

import cn.yiidii.panel.core.vo.ResponseStatusEnum;
import lombok.Data;

@Data
public class ServiceException extends RuntimeException {

    private int code;
    private String message;

//    @Override
//    public Throwable fillInStackTrace() {
//        return this;
//    }

    public ServiceException(String msg) {
        this.code = 7000;
        this.message = msg;
    }

    public ServiceException(ResponseStatusEnum status) {
        super(status.getMsg());
        this.code = status.getCode();
        this.message = status.getMsg();
    }
}
