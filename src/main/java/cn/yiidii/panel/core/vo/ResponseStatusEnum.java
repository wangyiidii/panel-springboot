package cn.yiidii.panel.core.vo;

public enum ResponseStatusEnum {

    JWT_EXPIRED(1001, "Expire JWT"),
    JWT_SIGNATURE_ERROR(1002, "Signature Error"),
    JWT_MALFORMED(1003, "Malformed JWT"),

    OK(2000, "操作成功"),//成功

    BAD_REQUSET(4000, "Bad Request"),//
    NO_PERMISSION(4001, "没有权限"),//

    UNKNOWN_ERROR(5000, "未知异常"),//未知异常

    USER_ALREADY_LOGIN(6000, "用户已经登录"),
    USER_NOT_FOUND(6001, "用户不存在"),
    PWD_INCORRECT(6002, "密码不正确"),
    INCORRECT_USERNAME_OR_PASSWORD(6003, "用户名或密码不正确"),
    USERNAME_ALREADY_EXISTS(6004, "用户名已存在"),
    USER_DISABLED(6005, "账户未激活或已被禁用"),
    USER_UNAUTH(6006, "用户未登录"),
    USER_ALREADY_ACTIVED(6007, "用户已被激活"),
    TOKEN_AUTH_FAILED(6008, "token认证失败"),

    SERVICE_ERROR(7000, "未知服务异常");

    private int code;
    private String msg;

    private ResponseStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
