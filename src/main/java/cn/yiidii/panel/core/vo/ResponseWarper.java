package cn.yiidii.panel.core.vo;

public class ResponseWarper {

    private ResponseWarper() {
        throw new UnsupportedOperationException();
    }

    public static <T> Response<T> success() {
        return success("success", true);
    }

    public static <T> Response<T> success(String msg, boolean isMsg) {
        if (isMsg) {
            return new Response(ResponseStatusEnum.OK.getCode(), msg, true);
        } else {
            return new Response(ResponseStatusEnum.OK.getCode(), "", msg);
        }
    }

    public static <T> Response<T> success(String msg, T data) {
        return new Response(ResponseStatusEnum.OK.getCode(), msg, data);
    }

    public static <T> Response<T> success(T data) {
        return new Response(ResponseStatusEnum.OK, data);
    }

    public static <T> Response<T> error() {
        return error(ResponseStatusEnum.UNKNOWN_ERROR);
    }

    public static <T> Response<T> error(int code, String msg) {
        return new Response(code, msg, false);
    }

    public static <T> Response<T> error500() {
        return new Response(ResponseStatusEnum.UNKNOWN_ERROR, false);
    }

    public static <T> Response<T> error500(String msg) {
        return new Response(5000, msg, false);
    }

    public static <T> Response<T> serviceError(String msg) {
        return new Response(ResponseStatusEnum.SERVICE_ERROR.getCode(), msg, false);
    }

    public static <T> Response<T> serviceError(int code, String msg) {
        return new Response(code, msg, false);
    }


    public static <T> Response<T> error(ResponseStatusEnum status) {
        return new Response(status.getCode(), status.getMsg(), false);
    }


    public static <T> Response<T> error(ResponseStatusEnum status, T data) {
        return new Response(status, data);
    }

//    public static void error(HttpStatus status, HttpServletResponse response) throws IOException {
//        write(status, "", response);
//    }
//
//    public static <T> void error(HttpStatus status, T data, HttpServletResponse response) throws IOException {
//        write(status, data, response);
//    }
//
//    private static <T> void write(HttpStatus status, T data, HttpServletResponse response) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        Writer writer = response.getWriter();
//        ObjectWriter objectWriter = mapper.writer();
//        objectWriter.writeValue(writer, new Response(status, data));
//    }
}
