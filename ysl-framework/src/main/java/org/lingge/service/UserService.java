package org.lingge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
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
}
