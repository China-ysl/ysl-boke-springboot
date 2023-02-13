package org.lingge.Filter;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.lingge.constants.SystemConstants;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.LoginUser;
import org.lingge.enums.AppHttpCodeEnum;
import org.lingge.utils.JwtUtil;
import org.lingge.utils.RedisCache;
import org.lingge.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader(SystemConstants.TOKEN);
        //请求头中不一定有token
        if (!StringUtils.hasText(token)){
            //直接放行
            filterChain.doFilter(request,response);
            return;
        }
        //解析获取userid
        Claims claims = null;
        try {
            //通过JwtUtil工具类中的parseJWT方法解析从响应头中获取的token
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            //两种抛异常的情况 1 token超时  2 token非法
            e.printStackTrace();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response,JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject( );
        //从redis获取用户的信息
        LoginUser loginUser = redisCache.getCacheObject(SystemConstants.BLOG_LOGIN + userId);
        if (Objects.isNull(loginUser)){
            //如果获取不到
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response,JSON.toJSONString(result));
            return;
        }
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        //放行
        filterChain.doFilter(request,response);
    }
}
