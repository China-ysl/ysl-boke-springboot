package org.lingge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-02-05 02:06:21
 */
public interface UserService extends IService<User> {

    ResponseResult userinfo();

    ResponseResult updtaeUserinfo(User user);

    ResponseResult register(User user);

    ResponseResult selectUserPage(User user, Integer pageNum, Integer pageSize);

    boolean checkUserNameUnique(String userName);

    boolean checkPhoneUnique(User user);

    boolean checkEmailUnique(User user);

    ResponseResult addUser(User user);

    ResponseResult updateUser(User user);
}
