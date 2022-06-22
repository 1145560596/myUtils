package com.atme.utils.my.result;

import com.atme.utils.my.result.exception.BaseException;
import com.atme.utils.my.result.exception.ViewResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author amao
 * @create 2022-06-21-16:42
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<?> jsonErrorHandler(HttpServletRequest request, Exception ex) {
        Result<Object> result = new Result<>();
        if (ex instanceof BaseException) {
            BaseException serviceException = (BaseException) ex;
            result.setStatus(serviceException.getStatus());
            result.setMsg(serviceException.getMessage());
            result.setData(serviceException.getData());
        } else {
            result.setStatus(ViewResultCodeEnum.UNKNOWN_EXCEPTION.getCode());
            result.setMsg((ViewResultCodeEnum.UNKNOWN_EXCEPTION.getMsg()));
            ex.printStackTrace();
            log.error("全局捕获到异常:{}", ex.getMessage());
        }
        return result;
    }

}
