package org.lingge.handler.exception;

import lombok.extern.slf4j.Slf4j;
import org.lingge.domain.ResponseResult;
import org.lingge.enums.AppHttpCodeEnum;
import org.lingge.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //自定义异常
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExecptionHandler(SystemException e){
        //打印异常信息方便追溯
        log.error("出现了异常!",e);
        //从异常对象中获取提示信息且封装返回

        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    //自定义异常以外的异常
    @ExceptionHandler(Exception.class)
    public ResponseResult execptionHandler(Exception e){
        //打印异常信息方便追溯
        log.error("出现了异常!",e);
        //从异常对象中获取提示信息且封装返回

        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}
