package org.lingge.service.impl;

import org.lingge.constants.SystemConstants;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.LoginUser;
import org.lingge.domain.entity.User;
import org.lingge.domain.vo.BlogUserLoginVo;
import org.lingge.domain.vo.UserinfoVo;
import org.lingge.service.BlogLoginService;
import org.lingge.utils.BeanCopyUtils;
import org.lingge.utils.JwtUtil;
import org.lingge.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceimpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    //注入redis工具类
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        //判断是否验证通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取用户id生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);
        //把用户存入Redis
        redisCache.setCacheObject(SystemConstants.BLOG_LOGIN+userid,loginUser);
        //把token和userinof封装返回
        //把User转换成userinfo
        UserinfoVo userinfo = BeanCopyUtils.copyBean(loginUser.getUser(), UserinfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userinfo);
        System.out.println(userinfo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult lonout() {
        //从SecurityContextHolder获取token解析出用户的userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loguser = (LoginUser) authentication.getPrincipal();
        //从LoginUser中获取userid
        Long userid = loguser.getUser().getId();
        redisCache.deleteObject(SystemConstants.BLOG_LOGIN+userid);
        //根据uuid删除redis中的用户
        return ResponseResult.okResult();
    }
}
