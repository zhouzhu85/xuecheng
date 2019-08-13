package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  统一异常捕获类
 *  controllerAdvice：控制器增强
 * @author zhouzhu
 * @Description
 * @create 2019-08-13 15:40
 */
@ControllerAdvice
public class ExceptionCatch {

    private static final Logger LOGGER= LoggerFactory.getLogger(ExceptionCatch.class);

    /** 使用EXCEPTIONS存放异常类型和错误代码的映射，ImmutableMap的特点的一旦创建不可改变，并且线程安全 **/
    private static ImmutableMap<Class<? extends Throwable>,ResultCode> EXCEPTIONS;

    /** 使用builder来创建一个异常类型和错误代码的异常 **/
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder=ImmutableMap.builder();

    /**
     * 捕获exception异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e){
        LOGGER.error("catch exception:{}\r\nexception: ",e.getMessage(),e);
        if (EXCEPTIONS==null){
            EXCEPTIONS=builder.build();
        }
        final ResultCode resultCode = EXCEPTIONS.get(e.getClass());
        final ResponseResult responseResult;
        //若捕获到
        if (resultCode!=null){
            responseResult=new ResponseResult(resultCode);
        }else {
            responseResult=new ResponseResult(CommonCode.SERVER_ERROR);
        }
        return responseResult;
    }

    /**
     * 捕获CustomException异常类
     * @param customException
     * @return
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException customException){
        //记录日志
        LOGGER.error("catch exception:{}",customException.getMessage());
        ResultCode resultCode=customException.getResultCode();
        return new ResponseResult(resultCode);
    }

    /**
     * 加入一些基础的异常类型判断
     */
    static {
        builder.put(HttpMessageNotReadableException.class,CommonCode.INVALID_PARAM);
        builder.put(HttpRequestMethodNotSupportedException.class,CommonCode.REQUEST_ERROR);
    }
}
