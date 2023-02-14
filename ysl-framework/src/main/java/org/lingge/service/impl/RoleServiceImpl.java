package org.lingge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lingge.constants.SystemConstants;
import org.lingge.domain.entity.Role;
import org.lingge.mapper.RoleMapper;
import org.lingge.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-02-13 23:04:51
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public List<String> selectUserRole(Long id) {
        //判断是否是管理员，如果是返回的集合中只需有admin
        if (id==1L){
            ArrayList<String> roleKey = new ArrayList<>();
            roleKey.add(SystemConstants.ADMIN);
            return roleKey ;
        }
        //否则查询用户所对应的角色信息
        return getBaseMapper().selectRoleKeyUserId(id);
    }
}
