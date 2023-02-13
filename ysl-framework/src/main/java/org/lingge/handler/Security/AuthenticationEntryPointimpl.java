package org.lingge.handler.Security;

import com.alibaba.fastjson.JSON;
import org.lingge.domain.ResponseResult;
import org.lingge.enums.AppHttpCodeEnum;
import org.lingge.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器AuthenticationEntryPointImpl
 */
@Component
public class AuthenticationEntryPointimpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //方便后期排查异常打印异常信息
        e.printStackTrace();
        ResponseResult result = null;

        //BadCredentialsException
        //InsufficientAuthenticationException
        if (e instanceof BadCredentialsException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),e.getMessage());
        }else if (e instanceof InsufficientAuthenticationException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else {
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
        //响应给前端
    }
}
