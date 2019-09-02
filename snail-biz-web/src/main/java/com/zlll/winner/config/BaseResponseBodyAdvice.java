package com.zlll.winner.config;

import com.zlll.winner.common.BaseResult;
import com.zlll.winner.exception.LoginException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

@RestControllerAdvice
public class BaseResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private static Log log = LogFactory.getLog(BaseResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.debug("BaseResponseBodyAdvice==>supports:" + converterType);
        log.debug("BaseResponseBodyAdvice==>supports:" + returnType.getClass());
        log.debug("BaseResponseBodyAdvice==>supports:"
                + MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType));
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body == null) {
            return body;
        }

        if (body instanceof BaseResult || body instanceof String) {
            return body;
        } else if (body instanceof File) {
            return body;
        } else {
            log.debug("BaseResponseBodyAdvice==>beforeBodyWrite:" + returnType + "," + body);
            BaseResult result = new BaseResult();
            result.setData(body);
            body = (Object) result;
            return body;
        }
    }

    /**
     * 400 - Bad Request
     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数解析失败", e);
        return getResult(HttpStatus.BAD_REQUEST,"参数解析失败:"+e.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     */
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        return getResult(HttpStatus.METHOD_NOT_ALLOWED,"不支持当前请求方法:"+e.getMessage());
    }

    /**
     * 415 - Unsupported Media Type
     */
//    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Object handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("不支持当前媒体类型", e);
        return getResult(HttpStatus.UNSUPPORTED_MEDIA_TYPE,"不支持当前媒体类型:"+e.getMessage());
    }

    /**
     * 500 - Internal Server Error
     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        log.error("服务运行异常", e);
        String msg = "";
        if (e instanceof UndeclaredThrowableException){
            InvocationTargetException targetEx =(InvocationTargetException)((UndeclaredThrowableException) e).getUndeclaredThrowable();
            if (targetEx.getTargetException() != null){
                msg = targetEx.getTargetException().getMessage();
            }
        } else {
            msg = e.getMessage();
        }
        if(e instanceof LoginException){
            return getResult("-999",((LoginException) e).getErrorMsg());
        }
        return getResult(HttpStatus.INTERNAL_SERVER_ERROR,msg);
    }

    private BaseResult getResult(HttpStatus httpStatus,String msg){
        BaseResult result = new BaseResult();
        result.setMsg(msg);
        result.setCode(String.valueOf(httpStatus.value()));
        return result;
    }
    private BaseResult getResult(String httpStatus,String msg){
        BaseResult result = new BaseResult();
        result.setMsg(msg);
        result.setCode(httpStatus);
        return result;
    }
}
