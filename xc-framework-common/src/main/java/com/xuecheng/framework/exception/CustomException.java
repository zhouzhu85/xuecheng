package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 *  自定义异常
 * @author zhouzhu
 * @Description
 * @create 2019-08-13 15:14
 */
public class CustomException extends RuntimeException{
    private ResultCode resultCode;
    public CustomException(ResultCode resultCode){
        super("错误代码："+resultCode.code()+"错误信息："+resultCode.message());
        this.resultCode=resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
