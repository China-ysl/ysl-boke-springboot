package org.lingge.handler.Security;

import com.alibaba.fastjson.JSON;
import org.lingge.domain.ResponseResult;
import org.lingge.enums.AppHttpCodeEnum;
import org.lingge.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
 /**
  * 授权失败处理器AccessDeniedHandlerimpl
  */
@Component
public class AccessDeniedHandlerimpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        //方便后期排查异常打印异常信息
        e.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
        //响应给前端
    }
}
