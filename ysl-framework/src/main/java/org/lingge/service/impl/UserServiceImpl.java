package org.lingge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.User;
import org.lingge.domain.entity.UserRole;
import org.lingge.domain.vo.PageVo;
import org.lingge.domain.vo.UserVo;
import org.lingge.domain.vo.UserinfoVo;
import org.lingge.enums.AppHttpCodeEnum;
import org.lingge.exception.SystemException;
import org.lingge.mapper.UserMapper;
import org.lingge.service.UserRoleService;
import org.lingge.service.UserService;
import org.lingge.utils.BeanCopyUtils;
import org.lingge.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-02-03 04:16:50
 */
@Service("userService")
public class  UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;
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

    @Override
    public ResponseResult selectUserPage(User user, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.hasText(user.getUserName()),User::getUserName,user.getUserName());
        queryWrapper.eq(StringUtils.hasText(user.getStatus()),User::getStatus,user.getStatus());
        queryWrapper.eq(StringUtils.hasText(user.getPhonenumber()),User::getPhonenumber,user.getPhonenumber());
        Page<User> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        //转换成VO
        List<User> users = page.getRecords();
        List<UserVo> userVoList = users.stream()
                .map(u -> BeanCopyUtils.copyBean(u, UserVo.class))
                .collect(Collectors.toList());
        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(userVoList);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public boolean checkUserNameUnique(String userName) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getUserName,userName))==0;
    }

    @Override
    public boolean checkPhoneUnique(User user) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getPhonenumber,user.getPhonenumber()))==0;
    }

    @Override
    public boolean checkEmailUnique(User user) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getEmail,user.getEmail()))==0;
    }

    @Override
    @Transactional
    public ResponseResult addUser(User user) {
        //密码加密处理
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);

        if(user.getRoleIds()!=null&&user.getRoleIds().length>0){
            insertUserRole(user);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateUser(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    private void insertUserRole(User user) {
        List<UserRole> sysUserRoles = Arrays.stream(user.getRoleIds())
                .map(roleId -> new UserRole(user.getId(), roleId)).collect(Collectors.toList());
        userRoleService.saveBatch(sysUserRoles);
    }

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