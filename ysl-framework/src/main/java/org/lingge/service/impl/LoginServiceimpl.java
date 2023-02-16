package org.lingge.service.impl;

import org.lingge.constants.SystemConstants;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.LoginUser;
import org.lingge.domain.entity.User;
import org.lingge.domain.vo.BlogUserLoginVo;
import org.lingge.domain.vo.UserinfoVo;
import org.lingge.service.LoginService;
import org.lingge.utils.BeanCopyUtils;
import org.lingge.utils.JwtUtil;
import org.lingge.utils.RedisCache;
import org.lingge.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginServiceimpl implements LoginService {

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
        redisCache.setCacheObject(SystemConstants.ADMIN_LOGIN+userid,loginUser);
        //把token封装返回
        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult lonout() {
        //获取当前登陆用户的id
        Long userId = SecurityUtils.getUserId();
        //删除对应redis中的用户信息
        redisCache.deleteObject(SystemConstants.ADMIN_LOGIN+userId);
        return ResponseResult.okResult();
    }
}
