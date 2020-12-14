package cn.yiidii.panel.advice;

import cn.yiidii.panel.core.vo.Response;
import cn.yiidii.panel.core.vo.ResponseStatusEnum;
import cn.yiidii.panel.core.vo.ResponseWarper;
import cn.yiidii.panel.ex.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public <T> Response<T> handleUnexceptedServer(ServiceException ex) {
        return ResponseWarper.serviceError(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public <T> Response<T> handleBindException(HttpServletRequest request, BindException exception) {
        List<FieldError> allErrors = exception.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError errorMessage : allErrors) {
            sb.append(errorMessage.getDefaultMessage()).append(" ");
        }
        return ResponseWarper.error(ResponseStatusEnum.BAD_REQUSET.getCode(), sb.toString());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public <T> Response<T> handleConstraintViolationException(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        StringBuilder stringBuilder = new StringBuilder();
        for (ConstraintViolation<?> item : violations) {
            stringBuilder.append(item.getMessage()).append(" ");
        }
        return ResponseWarper.error(HttpStatus.BAD_REQUEST.value(), stringBuilder.toString());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.OK)
    public <T> Response<T> ex(ServiceException ex) {
        return ResponseWarper.error(120,"all throw e");
    }

}
