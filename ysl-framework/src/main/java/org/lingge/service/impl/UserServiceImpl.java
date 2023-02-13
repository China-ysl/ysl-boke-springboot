package org.lingge.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.User;
import org.lingge.domain.vo.UserinfoVo;
import org.lingge.enums.AppHttpCodeEnum;
import org.lingge.exception.SystemException;
import org.lingge.mapper.UserMapper;
import org.lingge.service.UserService;
import org.lingge.utils.BeanCopyUtils;
import org.lingge.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-02-03 04:16:50
 */
@Service("userService")
public class  UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult userinfo() {
        //调用工具类SecurityUtils获取当前用户id
        Long userId = SecurityUtils.getUserId();

        //根据id获取用户信息
        User user =getById(userId);
        //封装成userinfoVo
        UserinfoVo userinfoVo = BeanCopyUtils.copyBean(user, UserinfoVo.class);
        return ResponseResult.okResult(userinfoVo);
    }

    @Override
    public ResponseResult updtaeUserinfo(User user) {
        LambdaUpdateWrapper<User> userUpdateWrapper = new LambdaUpdateWrapper<>();
        userUpdateWrapper.eq(User::getId,user.getId());
        userUpdateWrapper.set(User::getAvatar, user.getAvatar());
        userUpdateWrapper.set(User::getNickName, user.getNickName());
        userUpdateWrapper.set(User::getEmail, user.getEmail());
        userUpdateWrapper.set(User::getSex, user.getSex());
        update(userUpdateWrapper);
        return ResponseResult.okResult();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断 null 或 ""
        //用户名非空判断
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.THE_USERNAME_IS_NOT_EMPTY);
        }
        //昵称非空判断
        if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.THE_NICKNAME_IS_NOT_EMPTY);
        }
        //密码非空判断
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.THE_PASSWORD_IS_NOT_EMPTY);
        }
        //邮箱非空判断
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.THE_EMAIL_IS_NOT_EMPTY);
        }
//        对用户名否存在进行判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        //对昵称是否存在进行判断
        if(NickNameNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST );
        }
        //对邮箱是否存在进行判断
        if(EmailNameExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

//        对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        //将加密后的用户密码更新
        user.setPassword(encodePassword);
//        将用户存入写数据库
        save(user);
        return ResponseResult.okResult();}

    private boolean EmailNameExist(String email) {
//        邮箱是否存在
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getEmail, email);
        return count(updateWrapper) > 0;
    }

    private boolean NickNameNameExist(String nickName) {
        //昵称是否存在
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getNickName, nickName);
        return count(updateWrapper) > 0;
    }

    private boolean userNameExist(String userName) {
        //查询用户名是否存在
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getUserName, userName);
        return count(updateWrapper) > 0;
    }
}