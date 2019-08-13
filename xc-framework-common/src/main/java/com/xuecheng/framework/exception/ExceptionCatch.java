package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
}
