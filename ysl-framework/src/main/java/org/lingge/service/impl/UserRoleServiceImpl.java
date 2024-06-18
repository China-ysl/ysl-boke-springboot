package org.lingge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lingge.domain.entity.UserRole;
import org.lingge.mapper.UserRoleMapper;
import org.lingge.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2023-02-19 23:14:22
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
